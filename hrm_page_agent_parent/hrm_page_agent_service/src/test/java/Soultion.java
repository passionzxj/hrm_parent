import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Soultion {
    public static void soultion(int[] A, int[] B) {
        List<Integer> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (int i : B) {
            set.add(i);
        }
        for (int j : A) {
            if (!set.contains(j)) {
                list.add(j);
            }
        }
        for (int a:list){
            System.out.println(a+"");
        }
    }

    public static void main(String[] args) {
        int[] A ={1,2,3,5,3,6,8,1};
        int[] B={2,3,4};
        soultion(A,B);
    }
}
