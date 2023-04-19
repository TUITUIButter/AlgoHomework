# Week 8
## [235. 二叉搜索树的最近公共祖先](https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-search-tree/)
>
> 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。 
> 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。” 
> 例如，给定如下二叉搜索树:  root = [6,2,8,0,4,7,9,null,null,3,5]

```text
输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
输出: 6 
解释: 节点 2 和节点 8 的最近公共祖先是 6。

输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
输出: 2
解释: 节点 2 和节点 4 的最近公共祖先是 2, 因为根据定义最近公共祖先节点可以为节点本身。
```

### solution
两次遍历。采用先序遍历查找两个子节点的路径。
获取到路径之后从头开始遍历路径，第一个分叉点（第一个不同的值）之前的最后一个
父节点便是二叉搜索树的最近公共祖先。

### code
```python
def get_path(self, root: 'TreeNode', p, p_path):
    if root is None:
        return
    if root.val == p: # 找到节点
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
    for index in range(min(len_q, len_p)): # 从头开始遍历
        if p_path[index] == q_path[index]: # 相等
            last_parent = p_path[index]
        else:
            break

    return last_parent
```

### solution 2
一次遍历。同时遍历p与q，如果p<val<q，说明遇到了分叉点，复杂度没有降低，
但实际运行效率提高

### code 2
```python
def lowestCommonAncestor(self, root: TreeNode, p: TreeNode, q: TreeNode) -> TreeNode:
    ancestor = root  # 默认是根
    while True:
        if p.val < ancestor.val and q.val < ancestor.val:
            ancestor = ancestor.left
        elif p.val > ancestor.val and q.val > ancestor.val:
            ancestor = ancestor.right
        else:  # 遇到了p<val<q
            break
    return ancestor
```

### result
![](./p1-1.png)

## [110. 平衡二叉树](https://leetcode.cn/problems/balanced-binary-tree/description/)
>给定一个二叉树，判断它是否是高度平衡的二叉树。 
> 
> 本题中，一棵高度平衡二叉树定义为： 
> 
> 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。

```text
输入：root = [3,9,20,null,null,15,7]
输出：true

输入：root = [1,2,2,3,3,null,null,4,4]
输出：false
```

### solution
首先编写深度函数，遍历树
```python
def height(root: TreeNode) -> int:
    if not root:
        return 0
    return max(height(root.left), height(root.right)) + 1
```

直接进行判断与递归：
```python
if not root:
    return True
return abs(height(root.left) - height(root.right)) <= 1 and self.isBalanced(root.left) and self.isBalanced(root.right)
```

### result
![](./p2-1.png)

### solution 2
显然每个节点都需要计算深度，进行了很多次重复运算，因此可以优化计算次数：
自底向上递归，类似于后序遍历，对于当前遍历到的节点，先递归地判断其左右子树是否平衡，再判断以当前节点为根的子树是否平衡。
如果一棵子树是平衡的，则返回其高度（高度一定是非负整数），否则返回 −1。如果存在一棵子树不平衡，则整个二叉树一定不平衡。

```python
def height(root: TreeNode) -> int:
    if not root:
        return 0
    leftHeight = height(root.left)
    rightHeight = height(root.right)
    if leftHeight == -1 or rightHeight == -1 or abs(leftHeight - rightHeight) > 1:
        return -1
    else:
        return max(leftHeight, rightHeight) + 1

return height(root) >= 0
```

### result
![](p2-2.png)

## [257. 二叉树的所有路径](https://leetcode.cn/problems/binary-tree-paths/)
>给你一个二叉树的根节点 root ，按 任意顺序 ，返回所有从根节点到叶子节点的路径。
>
>叶子节点 是指没有子节点的节点。 

```text
输入：root = [1,2,3,null,5]
输出：["1->2->5","1->3"]
```

### solution
先序遍历。
```python
def binaryTreePaths(self, root: Optional[TreeNode]) -> List[str]:
    if root is None:
        return []
    res = []

    def get_path(node: 'TreeNode', path):
        path.append(node.val)
        if node.left is None and node.right is None: # 叶子节点
            res.append(path.copy())
        else:
            if node.left is not None:  # 左边递归
                get_path(node.left, path)
            if node.right is not None:  # 右边递归
                get_path(node.right, path)
        path.pop()  # 回溯

    get_path(root, [])
    res_str = []
    for p in res:  # 转换为字符串
        s = str(p[0])
        for n in p[1:]:
            s += ('->' + str(n))
        res_str.append(s)
    return res_str
```

### result
![](p3-1.png)

改方法由于传递的是path列表，所以需要回溯，若采用直接构造字符串，
则是值传递，不需要回溯，临时变量会自动销毁


