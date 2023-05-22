# Definition for a binary tree node.
import heapq
from collections import deque, Counter
from typing import List


class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right


class Word:
    def __init__(self, key, value):
        self.key = key
        self.value = value

    def __lt__(self, other):
        if self.value != other.value:
            return self.value < other.value
        return self.key > other.key  # 从小到大


class Solution:
    def findCheapestPrice(self, n: int, flights: List[List[int]], src: int, dst: int, k: int) -> int:
        f = [[float("inf")] * n for _ in range(k + 2)]
        f[0][src] = 0
        for t in range(1, k + 2):
            for j, i, cost in flights:
                f[t][i] = min(f[t][i], f[t - 1][j] + cost)

        ans = min(f[t][dst] for t in range(1, k + 2))
        return -1 if ans == float("inf") else ans


    def dfs(self, root: TreeNode, limit: int, parent: int) -> int:
        if root is None:
            return parent

        total = parent + root.val

        if root.left is not None and root.right is not None:
            var_l = self.dfs(root.left, limit, total)
            if var_l < limit:
                root.left = None

            var_r = self.dfs(root.right, limit, total)
            if var_r < limit:
                root.right = None

            return max(var_l, var_r)

        elif root.left is not None:
            var_l = self.dfs(root.left, limit, total)
            if var_l < limit:
                root.left = None
            return var_l
        elif root.right is not None:
            var_r = self.dfs(root.right, limit, total)
            if var_r < limit:
                root.right = None
            return var_r
        else:
            return total

    def sufficientSubset(self, root: TreeNode, limit: int) -> TreeNode:
        if root is None:
            return None
        var = self.dfs(root, limit, 0)
        if var < limit:
            return None
        else:
            return root

    def order_print(self, root):
        if root is None:
            return
        print(root.val)
        self.order_print(root.left)
        self.order_print(root.right)

    def shortestBridge(self, grid: list[list[int]]) -> int:
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

    def topKFrequent(self, words: List[str], k: int) -> List[str]:
        cnt = Counter(words)
        hp = []
        for key, value in cnt.items():
            heapq.heappush(hp, Word(key, value))
            while len(hp) > k:
                heapq.heappop(hp)
        hp.sort(reverse=True)
        return [x.key for x in hp]


if __name__ == '__main__':
    root = TreeNode(5)
    l11 = TreeNode(4)
    l12 = TreeNode(8)

    l21 = TreeNode(11)
    l22 = TreeNode(17)
    l23 = TreeNode(4)

    l31 = TreeNode(7)
    l32 = TreeNode(1)
    l33 = TreeNode(5)
    l34 = TreeNode(3)

    root.left = l11
    root.right = l12
    l11.left = l21
    l12.left = l22
    l12.right = l23

    l21.left = l31
    l21.right = l32
    l23.left = l33
    l23.right = l34

    solution = Solution()
    solution.order_print(root)
    print('-' * 10)
    solution.sufficientSubset(root, 22)
    solution.order_print(root)

    print(solution.topKFrequent(["i", "love", "leetcode", "i", "love", "coding"], 2))
