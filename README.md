With the rapidly development of Internet technology, network information has grown at an exponential rate. For a system that requires statistical analysis of data, the process of collecting data is tedious. Based on this reality, the distributed crawler system has gained an opportunity for development. The system multiplies the efficiency of crawler by coordinating within several servers. However, the distributed system increases the complexity while gaining efficiency. So developers need to consider various factors to ensure the regular work of this system.

The architecture of whole system is Client–server model. At first, task nodes register themselves to the central node. Then central node distributes data crawling tasks to those task nodes. Task nodes do some data crawling, and return data to the central node. Central node will store the results and allocate new tasks to task nodes, until all the webpages have been crawled. This architecture focus on scalability, load balancing and reliability of the system.

Main techniques are as below:
1. JMS(java message service) for delivering messages asynchronously between nodes, which has remarkable effect on decoupling. It will temporarily store the messages in some queues, waiting for a leisure process to handle them. These queues determine the routing of messages, so we only need to manage these static queues.

2. ActiveMQ from Apache as the JMS server, which has the feature of cross-language and cross-platform messages delivery. It has the characteristics of delay acceptance and it can keep the messages in order.

3. Central node uses thread pool to relieve the efficiency bottleneck caused by task accumulation.

4. Jsoup for crawling data from webpages. It's efficient, but requires the html codes of webpages are well organized, then Jsoup can read and extract data.

Things need to do: the database in the central node needs parallel design.
