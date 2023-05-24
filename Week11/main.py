from typing import List


class Solution:

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

    def shortestPathLength(self, graph: List[List[int]]) -> int:
        # 新建邻接矩阵
        n = len(graph)
        d = [[n + 1] * n for _ in range(n)]
        for i in range(n):
            for j in graph[i]:
                d[i][j] = 1

        # 使用 floyd 算法预处理出所有点对之间的最短路径长度
        for k in range(n):
            for i in range(n):
                for j in range(n):
                    d[i][j] = min(d[i][j], d[i][k] + d[k][j])

        # dp数组，状态压缩
        f = [[float("inf")] * (1 << n) for _ in range(n)]

        for mask in range(1, 1 << n):
            # 如果 mask 只包含一个 1，即 mask 是 2 的幂
            if (mask & (mask - 1)) == 0:
                u = bin(mask).count("0") - 1
                f[u][mask] = 0
            else:
                for u in range(n):
                    if mask & (1 << u):  # 从u开始，所以u必须在这个路经里
                        for v in range(n):
                            if (mask & (1 << v)) and u != v:  # v也在这个路经里，且不和u相等
                                f[u][mask] = min(f[u][mask], f[v][mask ^ (1 << u)] + d[v][u])

        ans = min(f[u][(1 << n) - 1] for u in range(n))
        return ans


if __name__ == '__main__':
    solution = Solution()
    res = solution.networkDelayTime([[1,2,1]], 2, 2)
    print(res)
