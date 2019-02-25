package net.treebear.kwifimanager.config;


import net.treebear.kwifimanager.R;

import java.util.ArrayList;

/**
 * @author Tinlone
 * @date 2018/3/27.
 */

public interface CollectionConfig {

    ArrayList<Integer> GUIDE_IMAGES =
            new ArrayList<Integer>() {
                {
                    add(R.drawable.ic_guide_1);
                    add(R.drawable.ic_guide_2);
                    add(R.drawable.ic_guide_3);
                }
            };
}
