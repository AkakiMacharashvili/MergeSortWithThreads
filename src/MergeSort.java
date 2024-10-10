import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MergeSort {
    static class mergeSort extends Thread{
        List<Integer> list;
        List<Integer> answer;
        public mergeSort(List<Integer> list){
            this.list = list;
        }
        @Override
        public void run() {
            if(list.size() > 2) {
                List<Integer> left = new ArrayList<>();
                List<Integer> right = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {
                    if (i < list.size() / 2) left.add(list.get(i));
                    else right.add(list.get(i));
                }

                mergeSort mergeLeft = new mergeSort(left);
                mergeSort mergeRight = new mergeSort(right);

                mergeLeft.start();
                mergeRight.start();

                try {
                    mergeRight.join();
                    mergeLeft.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                List<Integer> sortedLeft = mergeLeft.answer;
                List<Integer> sortedRight = mergeRight.answer;


                this.answer = merge(sortedLeft, sortedRight);

            }else{
                this.answer = sort(list);
            }

        }

        public List<Integer> sort(List<Integer> list){
            if(list.size() == 1){
                return list;
            }else{
                if(list.get(0) < list.get(1)){
                    return list;
                }else{
                    return List.of(list.get(1), list.get(0));
                }
            }
        }

        public List<Integer> merge(List<Integer> left, List<Integer> right){
            List<Integer> result = new ArrayList<>();
            int p1 = 0;
            int p2 = 0;
            if(left == null){
                return right;
            }

            if(right == null){
                return left;
            }

            while(p1 < left.size() && p2 < right.size()){
                if(left.get(p1) < right.get(p2)){
                    result.add(left.get(p1));
                    p1++;
                }else{
                    result.add(right.get(p2));
                    p2++;
                }
            }
            while(p1 < left.size()){
                result.add(left.get(p1));
                p1++;
            }

            while(p2 < right.size()){
                result.add(right.get(p2));
                p2++;
            }

            return result;
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < 1000; i++){
            int next = rand.nextInt(1000);
            list.add(next);
        }

        mergeSort mergeSort = new mergeSort(list);

        mergeSort.start();
        try {
            mergeSort.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for(var smt : mergeSort.answer){
            System.out.print(smt + " ");
        }

    }
}
