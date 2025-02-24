# MultiThreaded Dictionary 

This project aims to build a multi-threaded dictionary server that handles concurrent client-requested operations to demonstrate four main functionalities: query the
meaning of a word, add a new word and its meaning, remove an existing word, and update the meaning. The dictionary follows a client-server architecture which implements two
fundamental technologies — sockets and threads — at the lowest level of abstraction of network communication and concurrency. The reliable communications between server and
clients is demonstrated using TCP and errors or exceptions such as invalid inputs, failure in network commination, and dictionary file I/O are handled properly and notified in the
Graphical User Interface (GUI).

## Features
* Multithreaded Server: Handles multiple client connections simultaneously.
* Dictionary Operations: Supports querying, adding, removing, and updating words.
* Client GUI: Provides an intuitive graphical user interface for client interactions.
* Error Handling: Implements basic error handling for connection failures, invalid inputs, and dictionary file operations.
* Persistent Storage: Uses a JSON file to store dictionary entries, ensuring data persistence.
