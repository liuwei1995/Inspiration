package com.xinaliu.inspiration.entity;

/**
 * Created by liuwei on 2017/8/25 17:18
 */

public class ManHua {

    /**
     * end_num : 12
     * chapter_domain : zymkcdn.com
     * start_num : 1
     * source_url : http://www.manhuatai.com/asonline/270.html?from=kmhapp
     * rule : /comic/A/阿衰online/270话SM/$$.jpg
     * site_id : -1
     */

    private int end_num;
    private String chapter_domain;
    private int start_num;
    private String source_url;
    private String rule;
    private int site_id;

    public int getEnd_num() {
        return end_num;
    }

    public void setEnd_num(int end_num) {
        this.end_num = end_num;
    }

    public String getChapter_domain() {
        return chapter_domain;
    }

    public void setChapter_domain(String chapter_domain) {
        this.chapter_domain = chapter_domain;
    }

    public int getStart_num() {
        return start_num;
    }

    public void setStart_num(int start_num) {
        this.start_num = start_num;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
    }
}
