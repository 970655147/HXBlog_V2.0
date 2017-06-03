package com.hx.blog_v2.domain.comparator;

import com.hx.blog_v2.domain.po.ResourcePO;

import java.util.Comparator;

/**
 * ResourceSortComparator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/3/2017 12:33 PM
 */
public class ResourceSortComparator implements Comparator<ResourcePO> {

    @Override
    public int compare(ResourcePO o1, ResourcePO o2) {
        int sortDiff = o1.getSort() - o2.getSort();
        if (sortDiff != 0) {
            return sortDiff;
        }
        return o1.getName().compareTo(o2.getName());
    }
}
