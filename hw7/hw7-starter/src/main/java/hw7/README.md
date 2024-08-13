# Discussion
## 0.75 load factor
JdkHashMap:
Benchmark                                                      (fileName) Mode Cnt Score Error Units
JmhRuntimeTest.buildSearchEngine                               apache.txt avgt 2 184.290 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc    apache.txt avgt 2 1122207638.000 bytes
JmhRuntimeTest.buildSearchEngine                               jhu.txt avgt 2 0.0910 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc    jhu.txt avgt 2 18101099.000 bytes
JmhRuntimeTest.buildSearchEngine                               joanne.txt avgt 2 0.064 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc    joanne.txt avgt 2 180997919.000 bytes
JmhRuntimeTest.buildSearchEngine                               newegg.txt avgt 2 74.1094 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc    newegg.txt avgt 2 7342124793.000 bytes
JmhRuntimeTest.buildSearchEngine                               random164.txt avgt 2 512.91949 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc    random164.txt avgt 2 1470212010.000 bytes
JmhRuntimeTest.buildSearchEngine                               urls.txt avgt 2 0.0109 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc    urls.txt avgt 2 183987367.000 bytes

ChainingHashMap:
Benchmark                                                      (fileName) Mode Cnt Score Error Units
JmhRuntimeTest.buildSearchEngine                               apache.txt avgt 2 161.6892 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc    apache.txt avgt 2 1200003927.000 bytes
JmhRuntimeTest.buildSearchEngine                               jhu.txt avgt 2 0.189 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc    jhu.txt avgt 2 190582083.000 bytes
JmhRuntimeTest.buildSearchEngine                               joanne.txt avgt 2 0.071 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc    joanne.txt avgt 2 18008000.000 bytes
JmhRuntimeTest.buildSearchEngine                               newegg.txt avgt 2 72.098 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc    newegg.txt avgt 2 71971001.000 bytes
JmhRuntimeTest.buildSearchEngine                               random164.txt avgt 2 770.001 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc    random164.txt avgt 2 16900010.000 bytes
JmhRuntimeTest.buildSearchEngine                               urls.txt avgt 2 0.021 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc    urls.txt avgt 2 17561009.000 bytes

OpenAddressingHashMap.java
Benchmark                                                       (fileName) Mode Cnt Score Error Units
JmhRuntimeTest.buildSearchEngine                                apache.txt avgt 2 20190.429 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc     apache.txt avgt 2 89656398.000 bytes
JmhRuntimeTest.buildSearchEngine                                jhu.txt avgt 2 0.546 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc     jhu.txt avgt 2 16491065.000 bytes
JmhRuntimeTest.buildSearchEngine                                joanne.txt avgt 2 0.501 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc     joanne.txt avgt 2 16789698.070 bytes
JmhRuntimeTest.buildSearchEngine                                newegg.txt avgt 2 9245.266 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc     newegg.txt avgt 2 49579827.000 bytes
JmhRuntimeTest.buildSearchEngine                                random164.txt avgt 2 1421220.404 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc     random164.txt avgt 2 169251900.000 bytes
JmhRuntimeTest.buildSearchEngine                                urls.txt avgt 2 0.020 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc     urls.txt avgt 2 17598959.781 bytes



The time it takes per opertation for each file with a hash table implementation should be very similar,
however, when running the program. For example, when it comes to load factor of 0.75, the best hash tables time is JdkHashMap, followed by
very closely Chaining. However, OpenAddressing runs much slower compared to the other two. In which, this is 
expected since the load factor has much more effect on the run time for the algorithm compared to the 
other two. There is also to be considered the switch from linear probing to double hashing, 
in which the time increases since in double hashing there is less likely of any type of collision
when involving different keys. 

However, this doesn't mean that OpenAddressing always run slower. In fact, it looks like as the load factors increase, 
so will the runtime. Second, is Chaining, and then Jdk. Which is caused because rehashing taking much longer to run compared to probing.To optimize
OpenAdressing performance, I removed certain conditions and checking to make sure that, although it doesn't
remove the worse case scenio, it does reduce the runtime to be more efficent. One exampele if the check 'k != null && 
has(k)'. As I did more changes in the code, I realized that my implemention was linear due to the lack of one of my functions 
getIndex() in which is not efficient when it comes to exitting loops when a condition is not valid(aka null). Leading to
a linear search.

This leads to OpenAdressing to now somewhat match the other hashmaps shown. And it also decreased the load factor taking 
larger spaces allocation. However, for overall performance ChainingHashMap is shown to be better all arround as it is one 
of the most efficent when it comes to 0.75.