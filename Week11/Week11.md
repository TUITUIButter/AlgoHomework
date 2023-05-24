# Week 10
## [743. 网络延迟时间](https://leetcode.cn/problems/network-delay-time/description/)
>
> 有 n 个网络节点，标记为 1 到 n。
给你一个列表 times，表示信号经过 有向 边的传递时间。 times[i] = (ui, vi, wi)，其中 ui 是源节点，vi 是目标节点， wi 是一个信号从源节点传递到目标节点的时间。
现在，从某个节点 K 发出一个信号。需要多久才能使所有节点都收到信号？如果不能使所有节点收到信号，返回 -1 。

![img_1.png](img_1.png)
```text
输入：times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
输出：2
```

### solution
Dijkstra算法，求出从k开始到各个节点的距离，最大值
就是传播的时间。
```python
def networkDelayTime(self, times: list[list[int]], n: int, k: int) -> int:
    dis = [float('inf')] * n
    visited = [0] * n
    res = {}

    node = k - 1
    dis[node] = 0
    res[node] = 0
    visited[node] = 1

    while True:
        for way in times:
            a, b, t = way[0]-1, way[1]-1, way[2]
            #  从node开始的边
            if a != node:
                continue

            dis[b] = min(dis[b], dis[a] + t)

        min_cost = float('inf')
        min_index = k

        for k, v in enumerate(dis):
            #  记录最近的，不在访问列表里的节点
            if v < min_cost and visited[k] == 0:
                min_cost = v
                min_index = k

        if min_cost == float('inf'):  # 没找到可达路径
            return -1

        res[min_index] = int(dis[min_index])
        visited[min_index] = 1
        node = min_index

        if len(res) == n:
            res = sorted(res.items(), key=lambda x: x[1], reverse=True)
            return res[0][1]
```
写的比较冗余，效率不高但是很好理解
![img.png](img.png)

