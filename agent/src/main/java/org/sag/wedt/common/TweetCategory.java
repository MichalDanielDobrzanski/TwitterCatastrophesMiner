package org.sag.wedt.common;

import java.io.Serializable;

/**
 * Created by Michał Breiter.
 */
public class TweetCategory implements Serializable {

    private static final String catFile = "src/main/java/org/sag/wedt/common/categories.txt";

    //private static final List<String> categories = new ArrayList<String>(); // static list of agent system categories.
//    static {
//        File f = new File(catFile);
//        System.out.println(f.getAbsolutePath());
//        try {
//            Scanner sc = new Scanner(new File(catFile));
//            while (sc.hasNext()) {
//                String cat = sc.next();
//                categories.add(cat);
//                sc.next();
//                sc.next();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    private String category;

    public TweetCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }


}
