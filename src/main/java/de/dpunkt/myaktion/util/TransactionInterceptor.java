package de.dpunkt.myaktion.util;

import javax.annotation.Resource;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.transaction.UserTransaction;

/**
 * @author Julian
 */
public class TransactionInterceptor {
    @Resource
    private UserTransaction transaction;

    @AroundInvoke
    public Object doTransaction(InvocationContext ctx) throws Exception {
        try {
            transaction.begin();
            
            Object ret = ctx.proceed();
            
            transaction.commit();
            
            return ret;
        } catch (Exception e) {
            try {
                transaction.rollback();
                System.err.println("addCampaign – Transaktion wurde zurückgerollt.");
            } catch (Exception e2) {
                System.err.println("addCampaign – Fehler beim Zurückrollen von Transaktion.");
            }
            throw e;
        }
    }
}