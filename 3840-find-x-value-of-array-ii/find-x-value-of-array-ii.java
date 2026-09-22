class Solution {
    static class Node {
        int prod;
        int[] count; // count[r] = number of prefixes within this segment whose product % k == r

        Node(int k) {
            this.prod = 1;
            this.count = new int[k];
        }
    }

    private int k;
    private Node[] tree;
    private int n;

    // Combine left and right child nodes
    private Node merge(Node left, Node right) {
        Node res = new Node(k);
        res.prod = (left.prod * right.prod) % k;

        // Prefixes entirely within the left child
        for (int r = 0; r < k; r++) {
            res.count[r] += left.count[r];
        }

        // Prefixes extending into the right child
        for (int r = 0; r < k; r++) {
            int newRem = (left.prod * r) % k;
            res.count[newRem] += right.count[r];
        }

        return res;
    }

    private void build(int node, int l, int r, int[] nums) {
        if (l == r) {
            tree[node] = new Node(k);
            int rem = nums[l] % k;
            tree[node].prod = rem;
            tree[node].count[rem] = 1;
            return;
        }
        int mid = l + (r - l) / 2;
        build(2 * node, l, mid, nums);
        build(2 * node + 1, mid + 1, r, nums);
        tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
    }

    private void update(int node, int l, int r, int idx, int val) {
        if (l == r) {
            int rem = val % k;
            tree[node] = new Node(k);
            tree[node].prod = rem;
            tree[node].count[rem] = 1;
            return;
        }
        int mid = l + (r - l) / 2;
        if (idx <= mid) {
            update(2 * node, l, mid, idx, val);
        } else {
            update(2 * node + 1, mid + 1, r, idx, val);
        }
        tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
    }

    // Traverse segments covering [ql, qr] in left-to-right order
    private int currProd;
    private int[] totalCount;

    private void query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            for (int rem = 0; rem < k; rem++) {
                int combinedRem = (currProd * rem) % k;
                totalCount[combinedRem] += tree[node].count[rem];
            }
            currProd = (currProd * tree[node].prod) % k;
            return;
        }

        int mid = l + (r - l) / 2;
        if (ql <= mid) {
            query(2 * node, l, mid, ql, qr);
        }
        if (qr > mid) {
            query(2 * node + 1, mid + 1, r, ql, qr);
        }
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.k = k;
        this.n = nums.length;
        this.tree = new Node[4 * n];

        build(1, 0, n - 1, nums);

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // 1. Persistent update to nums[index]
            update(1, 0, n - 1, index, value);

            // 2. Query range [start, n - 1]
            this.currProd = 1;
            this.totalCount = new int[k];
            query(1, 0, n - 1, start, n - 1);

            result[i] = totalCount[x];
        }

        return result;
    }
}