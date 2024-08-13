# Discussion
## 0.75 load factor
JdkHashMap:
Benchmark (fileName) Mode Cnt Score Error Units
JmhRuntimeTest.buildSearchEngine apache.txt avgt 2 157.950 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc apache.txt avgt 2 112261768.000 bytes
JmhRuntimeTest.buildSearchEngine jhu.txt avgt 2 0.175 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc jhu.txt avgt 2 17756528.000 bytes
JmhRuntimeTest.buildSearchEngine joanne.txt avgt 2 0.058 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc joanne.txt avgt 2 18098768.000 bytes
JmhRuntimeTest.buildSearchEngine newegg.txt avgt 2 74.094 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc newegg.txt avgt 2 73226052.000 bytes
JmhRuntimeTest.buildSearchEngine random164.txt avgt 2 537.960 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc random164.txt avgt 2 1469972152.000 bytes
JmhRuntimeTest.buildSearchEngine urls.txt avgt 2 0.011 ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc urls.txt avgt 2 17357868.000 bytes
