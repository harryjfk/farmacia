package pe.gob.minsa.farmacia.controlUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
import pe.gob.minsa.farmacia.services.impl.ConceptoService;

public class SelectJdbcTag extends RequestContextAwareTag {

    private SelectType selectType;

    @Autowired
    ConceptoService conceptoService;

    public SelectType getSelectType() {
        return selectType;
    }

    public void setSelectType(SelectType selectType) {
        this.selectType = selectType;
    }

    @Override
    protected int doStartTagInternal() throws Exception {
        if (conceptoService == null) {
            WebApplicationContext wac = getRequestContext().getWebApplicationContext();
            AutowireCapableBeanFactory acbf = wac.getAutowireCapableBeanFactory();
            acbf.autowireBean(this);
        }
        
        JspWriter out = pageContext.getOut();
        out.write("<script>console.log('ejecute script')</script>");
        return SKIP_BODY;
    }
}
