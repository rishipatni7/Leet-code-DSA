class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        if ((m + n - 1) % 2 != 0) {
            return false;
        }
        
        if (grid[0][0] == ')' || grid[m - 1][n - 1] == '(') {
            return false;
        }
        
        boolean[][][] visited = new boolean[m][n][m + n];
        
        return dfs(grid, 0, 0, 0, visited);
    }
    
    private boolean dfs(char[][] grid, int i, int j, int bal, boolean[][][] visited) {
        if (grid[i][j] == '(') {
            bal++;
        } else {
            bal--;
        }
        
        if (bal < 0) {
            return false;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        
        int remainingSteps = (m - 1 - i) + (n - 1 - j);
        if (bal > remainingSteps) {
            return false;
        }
        
        if (i == m - 1 && j == n - 1) {
            return bal == 0;
        }
        
        if (visited[i][j][bal]) {
            return false;
        }
        
        visited[i][j][bal] = true;
        
        if (i + 1 < m && dfs(grid, i + 1, j, bal, visited)) {
            return true;
        }
        
        if (j + 1 < n && dfs(grid, i, j + 1, bal, visited)) {
            return true;
        }
        
        return false;
    }
}