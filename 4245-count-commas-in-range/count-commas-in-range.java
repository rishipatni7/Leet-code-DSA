class Solution {
    public int countCommas(int n) {

        int count = 0;

        for (int i = 1000; i <= n; i++) {
            count++;
        }

        return count;
    }
}