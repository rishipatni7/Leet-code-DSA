class Solution {
    public int thirdMax(int[] nums) {

        int first = Integer.MIN_VALUE;
        int second = Integer.MIN_VALUE;
        int third = Integer.MIN_VALUE;

        boolean firstFound = false;
        boolean secondFound = false;
        boolean thirdFound = false;

        for (int num : nums) {

            if ((firstFound && num == first) ||
                (secondFound && num == second) ||
                (thirdFound && num == third)) {
                continue;
            }

            if (!firstFound || num > first) {
                third = second;
                thirdFound = secondFound;

                second = first;
                secondFound = firstFound;

                first = num;
                firstFound = true;
            }
            else if (!secondFound || num > second) {
                third = second;
                thirdFound = secondFound;

                second = num;
                secondFound = true;
            }
            else if (!thirdFound || num > third) {
                third = num;
                thirdFound = true;
            }
        }

        return thirdFound ? third : first;
    }
}