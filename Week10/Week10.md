# Week 10
## [787. K 站中转内最便宜的航班](https://leetcode.cn/problems/cheapest-flights-within-k-stops/)
>
> 有 n 个城市通过一些航班连接。给你一个数组 flights ，其中 flights[i] = [fromi, toi, pricei] ，表示该航班都从城市 fromi 开始，
> 以价格 pricei 抵达 toi。
现在给定所有的城市和航班，以及出发城市 src 和目的地 dst，
> 你的任务是找到出一条最多经过 k 站中转的路线，使得从 src 到 dst 的 价格最便宜，
> 并返回该价格。 如果不存在这样的路线，则输出 -1。

```text
输入: 
n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
src = 0, dst = 2, k = 1
输出: 200

输入: 
n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
src = 0, dst = 2, k = 0
输出: 500
```
### solution
动态规划：由src到i中转t次的航班可以由scr到j->j到i，总共t+1次的中转完成。
初始化时需要将动态数组置无穷大。

```python
def findCheapestPrice(self, n: int, flights: List[List[int]], src: int, dst: int, k: int) -> int:
    #  初始化为无穷
    dp = [[float("inf")] * n for _ in range(k + 2)]
    dp[0][src] = 0
    
    #  转移方程
    for i in range(1, k + 2):
        for f, j, cost in flights:
            dp[i][j] = min(dp[i][j], dp[i - 1][f] + cost)

    ans = min(dp[i][dst] for i in range(1, k + 2))
    return -1 if ans == float("inf") else ans
```

![img_5.png](img_5.png)

## [934. 最短的桥](https://leetcode.cn/problems/shortest-bridge/)
>
> 给你一个大小为 n x n 的二元矩阵 grid ，其中 1 表示陆地，0 表示水域。
岛 是由四面相连的 1 形成的一个最大组，即不会与非组内的任何其他 1 相连。grid 中 恰好存在两座岛 。
你可以将任意数量的 0 变为 1 ，以使两座岛连接起来，变成 一座岛 。
返回必须翻转的 0 的最小数目。。

```text
输入：grid = [[0,1],[1,0]]
输出：1

输入：grid = [[0,1,0],[0,0,0],[0,0,1]]
输出：2
```

### solution
首先找到其中一座岛的第一个陆地，
采用广度优先遍历扩展至整个岛屿。
接着继续遍历第一个岛屿的地面，采用广度/深度优先遍历，直至触碰到另一个未被遍历的土地。
```python
def shortestBridge(self, grid: List[List[int]]) -> int:
    # 首先进行广度优先搜索，找到第一个岛屿
    n = len(grid)

    begin_x, begin_y = 0, 0
    found = False
    # 枚举
    for i, row in enumerate(grid):
        for j, v in enumerate(row):
            if v != 1:
                continue
            else:
                begin_x, begin_y = i, j
                found = True
                break
        if found:
            break

    island = []
    grid[begin_x][begin_y] = -1  # 记录遍历过了
    q = deque([(begin_x, begin_y)])
    while q:  # 队列中存在元素
        x, y = q.popleft()
        island.append((x, y))  # 记录为陆地
        for nx, ny in (x + 1, y), (x - 1, y), (x, y + 1), (x, y - 1):  # 四周
            if 0 <= nx < n and 0 <= ny < n and grid[nx][ny] == 1:
                grid[nx][ny] = -1
                q.append((nx, ny))

    # 完成循环时island中全都为第一个岛屿
    step = 0
    q = island
    while True:
        tmp = q
        q = []
        for x, y in tmp:
            for nx, ny in (x + 1, y), (x - 1, y), (x, y + 1), (x, y - 1):
                if 0 <= nx < n and 0 <= ny < n:
                    if grid[nx][ny] == 1:
                        return step
                    if grid[nx][ny] == 0:
                        grid[nx][ny] = -1
                        q.append((nx, ny))
        step += 1
```
![img.png](img.png)

## [692. 前K个高频单词](https://leetcode.cn/problems/top-k-frequent-words/)
>
> 给定一个单词列表 words 和一个整数 k ，返回前 k 个出现次数最多的单词。
返回的答案应该按单词出现频率由高到低排序。如果不同的单词有相同出现频率， 按字典顺序排序。

```text
输入: words = ["i", "love", "leetcode", "i", "love", "coding"], k = 2
输出: ["i", "love"]
解析: "i" 和 "love" 为出现次数最多的两个单词，均为2次。
    注意，按字母顺序 "i" 在 "love" 之前。

输入: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
输出: ["the", "is", "sunny", "day"]
解析: "the", "is", "sunny" 和 "day" 是出现次数最多的四个单词，
    出现次数依次为 4, 3, 2 和 1 次。
```

### solution
创建一个小根优先队列。将每一个字符串插入到优先队列中，
如果优先队列的大小超过了k，那么我们就将优先队列顶端元素弹出。
这样最终优先队列中剩下的 k 个元素就是前 k 个出现次数最多的单词。
```python
def topKFrequent(self, words: List[str], k: int) -> List[str]:
    cnt = Counter(words)
    hp = []
    for key, value in cnt.items():
        heapq.heappush(hp, Word(key, value))
        while len(hp) > k:
            heapq.heappop(hp)
    hp.sort(reverse=True)
    return [x.key for x in hp]

class Word:
    def __init__(self, key, value):
        self.key = key
        self.value = value

    def __lt__(self, other):
        if self.value != other.value:
            return self.value < other.value
        return self.key > other.key  # 从小到大
```
![img_2.png](img_2.png)
