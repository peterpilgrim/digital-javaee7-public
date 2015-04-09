package uk.co.xenonique.digital.product.utils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;

/**
 * The type DataPopulator
 *
 * @author Peter Pilgrim
 */
@Singleton
@Startup
public class DataPopulator {

    @Inject
    private ExtendedPersistenceLoaderBean loaderBean;

    @Inject
    private PersistenceScriptLoaderBean scriptLoaderBean;

    @Resource
    private TimerService timerService;

    private boolean initialized = false;

    @PostConstruct
    public void populate()
    {
        System.out.printf("***** %s.populate() loaderBean=%s\n", this.getClass().getName(), loaderBean);

//        final ScheduleExpression expression = new ScheduleExpression().second("*/2").minute("*").hour("*");
//        timerService.createCalendarTimer(expression);

        loaderBean.createData();

//        scriptLoaderBean.executeScript();
    }

    @Timeout
    public void createInitialProjectData(Timer timer) {
        if (initialized) {
            return;
        }
        initialized = true;

        System.out.printf("***** %s.createInitialProjectData() loaderBean=%s, initialized=%s, timer=%s\n", getClass().getSimpleName(), loaderBean, initialized, timer);
        loaderBean.createData();
    }



}
