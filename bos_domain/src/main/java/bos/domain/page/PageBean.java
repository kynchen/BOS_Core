package bos.domain.page;/*
* @author kynchen
* 
* @date 2018/8/4 14:51
*
* @version idea
*/

import bos.domain.take_delivery.Promotion;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;
@XmlRootElement(name="pageBean")
@XmlSeeAlso({Promotion.class})
public class PageBean<T> {

    private Long totalCount;

    private List<T> pageData;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }
}
