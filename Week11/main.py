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


if __name__ == '__main__':
    solution = Solution()
    res = solution.networkDelayTime([[1,2,1]], 2, 2)
    print(res)
