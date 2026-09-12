import java.util.*;

class Solution {

    static class State {
        long score;
        List<Integer> indices;

        State(long score, List<Integer> indices) {
            this.score = score;
            this.indices = indices;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();
        int[][] arr = new int[n][4];

        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i;
        }

        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        int[] starts = new int[n];

        for (int i = 0; i < n; i++) {
            starts[i] = arr[i][0];
        }

        int[] next = new int[n];

        for (int i = 0; i < n; i++) {
            int left = i + 1;
            int right = n;

            while (left < right) {
                int mid = left + (right - left) / 2;

                if (starts[mid] > arr[i][1]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            next[i] = left;
        }

        State[][] dp = new State[n + 1][5];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= 4; j++) {
                dp[i][j] = new State(0, new ArrayList<>());
            }
        }

        for (int i = n - 1; i >= 0; i--) {

            for (int k = 1; k <= 4; k++) {

                State skip = dp[i + 1][k];

                State nextState = dp[next[i]][k - 1];

                List<Integer> list = new ArrayList<>(nextState.indices);
                list.add(arr[i][3]);
                Collections.sort(list);

                State take = new State(
                    nextState.score + arr[i][2],
                    list
                );

                if (isBetter(take, skip)) {
                    dp[i][k] = take;
                } else {
                    dp[i][k] = skip;
                }
            }
        }

        List<Integer> answer = dp[0][4].indices;

        int[] result = new int[answer.size()];

        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }

        return result;
    }

    private boolean isBetter(State a, State b) {

        if (a.score != b.score) {
            return a.score > b.score;
        }

        int n = Math.min(a.indices.size(), b.indices.size());

        for (int i = 0; i < n; i++) {
            if (!a.indices.get(i).equals(b.indices.get(i))) {
                return a.indices.get(i) < b.indices.get(i);
            }
        }

        return a.indices.size() < b.indices.size();
    }
}