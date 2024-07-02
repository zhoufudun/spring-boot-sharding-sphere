package com.oujiong.aop;

import com.oujiong.holder.SpringApplicationContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Stack;

@Aspect
@Configuration
public class TransactionAop {


    /**
     * 匹配
     *
     * @Value TransactionAnnotation 注解
     */
    @Pointcut("@annotation(com.test.aop.TransactionAnnotation)")
    public void manyTransaction() {
    }

    /**
     * @value 匹配controller 下的任意方法和任意参数
     */
    @Pointcut("execution(* com.test.controller..*(..))")
    public void executionController() {
    }


    /**
     * 环绕通知
     *
     * @param thisJoinPoint
     * @param annotation
     * @return
     * @throws Throwable
     */
    @Around(value = "manyTransaction()&&executionController()&&@annotation(annotation)")
    public Object around(ProceedingJoinPoint thisJoinPoint, TransactionAnnotation annotation) throws Throwable {
        Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack = new Stack<>();
        Stack<TransactionStatus> transactionStatuStack = new Stack<>();
        try {
            if (!openTransaction(dataSourceTransactionManagerStack, transactionStatuStack, annotation)) {
                return null;
            }
            //执行目标方法
            Object ret = thisJoinPoint.proceed();
            commit(dataSourceTransactionManagerStack, transactionStatuStack);
            return ret;
        } catch (Throwable e) {
            rollback(dataSourceTransactionManagerStack, transactionStatuStack);
            throw e;
        }
    }


    private boolean openTransaction(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                                    Stack<TransactionStatus> transactionStatuStack, TransactionAnnotation multiTransactional) {

        TransactionAnnotation.TransactionEnum[] value = multiTransactional.value();
        if (multiTransactional.value().length == 0) {
            return false;
        }
        for (TransactionAnnotation.TransactionEnum transactionEnum : value) {
            ApplicationContext applicationContext = SpringApplicationContextHolder.getApplicationContext();
            DataSourceTransactionManager dataSourceTransactionManager = (DataSourceTransactionManager) applicationContext.getBean(transactionEnum.getName());
            TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionDefinition());
            transactionStatuStack.push(transactionStatus);
            dataSourceTransactionManagerStack.push(dataSourceTransactionManager);
        }


        return true;
    }

    /**
     * 提交处理方法
     *
     * @param dataSourceTransactionManagerStack
     * @param transactionStatuStack
     */
    private void commit(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                        Stack<TransactionStatus> transactionStatuStack) {
        while (!dataSourceTransactionManagerStack.isEmpty()) {
            dataSourceTransactionManagerStack.pop().commit(transactionStatuStack.pop());
        }
    }

    /**
     * 回滚处理方法
     *
     * @param dataSourceTransactionManagerStack
     * @param transactionStatuStack
     */
    private void rollback(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                          Stack<TransactionStatus> transactionStatuStack) {
        while (!dataSourceTransactionManagerStack.isEmpty()) {
            dataSourceTransactionManagerStack.pop().rollback(transactionStatuStack.pop());
        }
    }
}