package ru.samara.bibliotek.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.util.EnumSet;

public class BibliotekDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {

        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {

        return new Class[]{BibliotekSpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() {

        return new String[]{"/"};
    }

    @Override
    public void onStartup(jakarta.servlet.ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        registerHiddenFieldFilter((jakarta.servlet.ServletContext) servletContext);
        registerCharacterEncodingFilter((ServletContext) servletContext);
    }

    //    @Override
//    public void onStartup(ServletContext aServletContext) throws ServletException {
//        super.onStartup(aServletContext);
//        registerHiddenFieldFilter(aServletContext);
//        registerCharacterEncodingFilter(aServletContext);
//    }
    private void registerHiddenFieldFilter(jakarta.servlet.ServletContext Context) {
        Context.addFilter("hiddenHttpMethodFilter", (jakarta.servlet.Filter) new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null, true,
                "/*");
    }

    private void registerCharacterEncodingFilter(jakarta.servlet.ServletContext Context) {
        EnumSet<jakarta.servlet.DispatcherType> dispatcherType =EnumSet.of(jakarta.servlet.DispatcherType.REQUEST,
                                        jakarta.servlet.DispatcherType.FORWARD);
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        jakarta.servlet.FilterRegistration.Dynamic characterEncoding = Context.addFilter("characterEncoding",
                (jakarta.servlet.Filter) characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherType, true, "/*");
    }
}

//    private void registerHiddenFieldFilter (ServletContext Context) {
//        Context.addFilter("hiddenHttpMethodFilter", (Filter) new HiddenHttpMethodFilter()).addMappingForUrlPatterns(
//                null, true, "/*");
//    }


//    private void registerCharacterEncodingFilter (ServletContext Context) {
//        EnumSet<DispatcherType> dispatcherType = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
//
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("UTF-8");
//        characterEncodingFilter.setForceEncoding(true);
//
//        FilterRegistration.Dynamic characterEncoding = Context.addFilter("characterEncoding", (Filter) characterEncodingFilter);
//        characterEncoding.addMappingForUrlPatterns(dispatcherType, true, "/*");


