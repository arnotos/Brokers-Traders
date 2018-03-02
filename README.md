# Brokers-Traders
Brokers&amp;Traders Darmstadt TP. Java projects with clients and servers talking to each other to simulate distributed systems

# Exercise 1
## Test the application
- Launch one sources->MultithreadedTCPServer.java
- Launch two or more sources->TCPClient.java

First TCPClient bash : 
- Write "b" to buy stock automatically.
- Press twice enter to send the trame to broker.

Second TCPClient bash : 
- Write "s" to sell stock automatically.
- Press twice enter to send the trame to broker.

With the option "m", you can choose your stock, quantity and price.


# Exercise 2
## Test the application
- Launch one rpc->PriceServer.java
- Launch two or more rpc->PriceServiceClient.java

First PriceServiceClient bash : 
- Write "a" to get the actual price.
- Press enter.
- Write "AAAA" to get the actual price for the AAAA stocks.
- Press enter.

Second PriceServiceClient bash : 
- Write "h".
- Press enter.
- Write "AAAA" to get historical price for the AAAA stocks.
- Press enter.


# Exercise 3
## Test the application
- Download ApacheMQ : http://activemq.apache.org/activemq-5153-release.html
- Extract it.
- WARNING : the file path doesn't contain a space !
- Move in directory apache-activemq-5.15.3 and run a console.
- Launch "bin/activemq start" command.

ApacheMQ is running.

- Launch one sources->MultithreadedTCPServer.java
- Launch two(2*) ApacheMQ->TCPClientListener.java

First TCPClientListener bash : 
- Write "a" for acyclic trader.
- Press enter to connect to the broker.

Second TCPClientListener bash : 
- Write "c" cyclic trader.
- Press enter to connect to the broker.

Then launch 1 ApacheMQ->Publisher.java

The News are catching by TCPClientListeners and deal between first and second trader can be done.