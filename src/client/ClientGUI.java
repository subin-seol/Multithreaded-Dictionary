package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import util.Constants;

public class ClientGUI extends JFrame {
    private Client client;
    private JTextField wordField;
    private JTextArea meaningField;
    private JTextArea dictionaryResultField;
    private JLabel errorLabel;

    public ClientGUI(String host, int port) {
        // Initialize GUI components
        setTitle(Constants.CLIENT_GUI_TITLE);
        setSize(Constants.GUI_WIDTH, Constants.GUI_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel titleLabel = new JLabel(Constants.CLIENT_GUI_TITLE, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(titleLabel, gbc);

        gbc.insets = new Insets(5, 10, 5, 10); 

        // Error Label
        errorLabel = new JLabel(" ", JLabel.CENTER); 
        errorLabel.setForeground(Color.RED);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(errorLabel, gbc);

        // Word Label and Field
        JLabel wordLabel = new JLabel("Word");
        wordField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(wordLabel, gbc);
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(wordField, gbc);

        // Meaning Label and Text Area
        JLabel meaningLabel = new JLabel("Meaning(s)");
        meaningField = new JTextArea(5, 20);
        JScrollPane meaningsScrollPane = new JScrollPane(meaningField);
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(meaningLabel, gbc);
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(meaningsScrollPane, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        // Query Button
        JButton queryButton = new JButton("Query");
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRequest("Query");
            }
        });
        buttonPanel.add(queryButton);

        // Add Button
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRequest("Add");
            }
        });
        buttonPanel.add(addButton);

        // Remove Button
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRequest("Remove");
            }
        });
        buttonPanel.add(removeButton);

        // Update Button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRequest("Update");
            }
        });
        buttonPanel.add(updateButton);

        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buttonPanel, gbc);

        // Result Label and Result Field
        JLabel resultLabel = new JLabel("Result");
        dictionaryResultField = new JTextArea(7, 29);
        dictionaryResultField.setLineWrap(true);
        dictionaryResultField.setWrapStyleWord(true);
        dictionaryResultField.setEditable(false);
        JScrollPane resultPane = new JScrollPane(dictionaryResultField);
        resultPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(resultLabel, gbc);
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(resultPane, gbc);

        // Done Button
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.closeConnection();
                dispose();
            }
        });
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 0, 0, 0);
        mainPanel.add(doneButton, gbc);

        add(mainPanel);
        setResizable(false);
        setVisible(true);

        client = new Client(host, port, this);

        if (!client.isConnectionSuccessful()) {
            displayError(Constants.FAILED_TO_CONNECT);
        }
    }

    private void handleRequest(String request) {
        String word = wordField.getText().trim();
        String meanings = meaningField.getText().trim();
        boolean submitSuccess = false;

        try {
            if (word.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please input a word", "Input Required", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            if ("Add".equals(request) || "Update".equals(request)) {
                if (meanings.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please input word meaning(s)", "Input Required", JOptionPane.PLAIN_MESSAGE);
                    return;
                } else {
                    client.getOut().write(request + " " + word + ":" + formatMeanings(meanings) + "\n");
                    submitSuccess = true;
                }
            } else if ("Query".equals(request) || "Remove".equals(request)) {
                client.getOut().write(request + " " + word + "\n");
                submitSuccess = true;
            }

            if (submitSuccess) {
                client.getOut().flush();
                wordField.setText("");
                meaningField.setText("");
                processServerResponse();
            }
        } catch (IOException e) {
            displayError(Constants.FAILED_TO_CONNECT);
        }
    }


    private String formatMeanings(String meanings) {
        return String.join("|", meanings.split("\n"));
    }

    private void processServerResponse() {
        try {
            String serverResponse = client.getIn().readLine();
            dictionaryResultField.setText("");
            dictionaryResultField.append(serverResponse);
        } catch (IOException e) {
            displayError(Constants.FAILED_TO_CONNECT);
        }
    }

    public void displayError(String errorMessage) {
        errorLabel.setText(errorMessage);
    }
}
