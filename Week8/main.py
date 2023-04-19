from typing import Optional, List


class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None


class Solution:

    def binaryTreePaths(self, root: Optional[TreeNode]) -> List[str]:
        if root is None:
            return []
        res = []

        def get_path(node: 'TreeNode', path):
            path.append(node.val)
            if node.left is None and node.right is None:
                res.append(path.copy())
            else:
                if node.left is not None:
                    get_path(node.left, path)
                if node.right is not None:
                    get_path(node.right, path)
            path.pop()

        get_path(root, [])
        res_str = []
        for p in res:
            s = str(p[0])
            for n in p[1:]:
                s += ('->' + str(n))
            res_str.append(s)
        return res_str


    def get_path(self, root: 'TreeNode', p, p_path):
        if root is None:
            return
        if root.val == p:
            p_path.append(p)
            return p_path

        p_path.append(root.val)
        tmp_p = self.get_path(root.left, p, p_path)
        if tmp_p is not None:
            return tmp_p
        tmp_q = self.get_path(root.right, p, p_path)
        if tmp_q is not None:
            return tmp_q
        p_path.pop()
        return tmp_q


    def lowestCommonAncestor(self, root: 'TreeNode', p: 'TreeNode', q: 'TreeNode') -> 'TreeNode':
        p_path = self.get_path(root, p.val, [])
        q_path = self.get_path(root, q.val, [])

        len_p = len(p_path)
        len_q = len(q_path)
        last_parent = root.val
        for index in range(min(len_q, len_p)):
            if p_path[index] == q_path[index]:
                last_parent = p_path[index]
            else:
                break

        return last_parent

    def isBalanced(self, root) -> bool:
        def height(root: TreeNode) -> int:
            if not root:
                return 0
            return max(height(root.left), height(root.right)) + 1

        if not root:
            return True
        return abs(height(root.left) - height(root.right)) <= 1 and self.isBalanced(root.left) and self.isBalanced(root.right)


if __name__ == '__main__':
    t1 = TreeNode(6)
    t2 = TreeNode(2)
    t3 = TreeNode(8)
    t4 = TreeNode(0)
    t5 = TreeNode(4)

    t1.left = t2
    t1.right = t3
    t2.left = t4
    t2.right = t5

    solution = Solution()
    res = solution.lowestCommonAncestor(t1, t4, t5)
    print(res)

    print(solution.binaryTreePaths(t4))
