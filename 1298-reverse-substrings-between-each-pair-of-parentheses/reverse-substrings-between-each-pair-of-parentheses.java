class Solution {
    public String reverseParentheses(String s) {
        int n = s.length();
        int[] pair = new int[n];
        int[] stack = new int[n];
        int top = -1;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack[++top] = i;
            } else if (c == ')') {
                int j = stack[top--];
                pair[i] = j;
                pair[j] = i;
            }
        }

        StringBuilder sb = new StringBuilder();
        int curr = 0;
        int step = 1;

        while (curr < n) {
            char c = s.charAt(curr);
            if (c == '(' || c == ')') {
                curr = pair[curr];
                step = -step;
            } else {
                sb.append(c);
            }
            curr += step;
        }

        return sb.toString();
    }
}