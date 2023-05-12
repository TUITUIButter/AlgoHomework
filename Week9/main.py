from collections import Counter
from heapq import heapify, heappushpop


def searchMatrix(matrix: list[list[int]], target: int) -> bool:
    m = len(matrix)
    n = len(matrix[0])

    x = 0
    y = n - 1

    while x < m and y >= 0:
        if matrix[x][y] == target:
            return True
        elif matrix[x][y] > target:
            y -= 1
            continue
        else:
            x += 1
            continue

    return False


def topKFrequent(nums: list[int], k: int) -> list[int]:
    cnt_pair = {}
    for i in nums:
        cnt = cnt_pair.get(i, 0)
        cnt_pair[i] = cnt + 1

    cnt_list = list(cnt_pair.items())
    cnt_list = sorted(cnt_list, key=lambda x: x[1], reverse=True)
    res = []
    for i in range(k):
        res.append(cnt_list[i][0])
    return res


def topKFrequent2(nums: list[int], k: int) -> list[int]:
    # 方法二：哈希+最小堆
    # 时间复杂度：O(n log k)
    cnt = Counter(nums)
    l = list(zip(cnt.values(), cnt.keys()))
    hp = l[:k]
    heapify(hp)
    for item in l[k:]:
        heappushpop(hp, item)
    return [item[1] for item in hp]


def guess(num: int) -> int:
    if num > 2:
        return -1
    elif num < 2:
        return 1
    return 0


def guessNumber(n: int) -> int:
    l = 0
    r = n
    mid = n // 2
    while guess(mid) != 0:
        mid = (l + r) // 2
        new_guess = guess(mid)
        if new_guess == -1:
            r = mid - 1
        elif new_guess == 1:
            l = mid + 1

    return mid


if __name__ == '__main__':
    res = searchMatrix(
        [[1, 4, 7, 11, 15], [2, 5, 8, 12, 19], [3, 6, 9, 16, 22], [10, 13, 14, 17, 24], [18, 21, 23, 26, 30]], 100)
    print(res)

    res = topKFrequent2([3, 0, 1, 0], 1)
    print(res)

    res = guessNumber(2)
    print(res)