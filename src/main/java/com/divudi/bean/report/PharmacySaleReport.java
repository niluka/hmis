/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.divudi.bean.report;

import com.divudi.bean.common.SessionController;
import com.divudi.bean.common.UtilityController;
import com.divudi.bean.memberShip.PaymentSchemeController;
import com.divudi.data.BillClassType;
import com.divudi.data.BillType;
import com.divudi.data.DepartmentType;
import com.divudi.data.FeeType;
import com.divudi.data.PaymentMethod;
import com.divudi.data.dataStructure.DatedBills;
import com.divudi.data.dataStructure.PharmacyDetail;
import com.divudi.data.dataStructure.PharmacyPaymetMethodSummery;
import com.divudi.data.dataStructure.PharmacySummery;
import com.divudi.data.dataStructure.SearchKeyword;
import com.divudi.data.table.String1Value3;
import com.divudi.data.table.String1Value6;
import com.divudi.data.table.String2Value4;
import com.divudi.ejb.CommonFunctions;
import com.divudi.entity.Bill;
import com.divudi.entity.BillItem;
import com.divudi.entity.BilledBill;
import com.divudi.entity.CancelledBill;
import com.divudi.entity.Category;
import com.divudi.entity.Department;
import com.divudi.entity.Institution;
import com.divudi.entity.Item;
import com.divudi.entity.PaymentScheme;
import com.divudi.entity.PreBill;
import com.divudi.entity.RefundBill;
import com.divudi.entity.pharmacy.ItemBatch;
import com.divudi.entity.pharmacy.PharmaceuticalBillItem;
import com.divudi.entity.pharmacy.Stock;
import com.divudi.facade.BillFacade;
import com.divudi.facade.BillItemFacade;
import com.divudi.facade.DepartmentFacade;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.TemporalType;

/**
 *
 * @author safrin
 */
@Named
@SessionScoped
public class PharmacySaleReport implements Serializable {

    Category category;
    Item item;
    private Date fromDate;
    private Date toDate;
    List<String1Value6> saleValuesCash;
    List<String1Value6> saleValuesCheque;
    List<String1Value6> saleValuesSlip;
    List<String1Value6> saleValuesCard;
    List<String1Value6> saleValuesCredit;
    List<String1Value6> bhtIssues;
    List<String1Value6> unitIssues;
    List<BillItem> billItems;
    private Department department;
    Department toDepartment;
    private Institution institution;
    private double grantNetTotal;
    private double grantTotal;
    private double grantProfessional;
    private double grantDiscount;
    private double grantCashTotal;
    private double grantCreditTotal;
    private double grantCardTotal;
    ///////
    private PharmacySummery billedSummery;
    private PharmacyDetail billedDetail;
    private PharmacyDetail cancelledDetail;
    private PharmacyDetail refundedDetail;
    private PharmacyPaymetMethodSummery billedPaymentSummery;
    //  private List<DatedBills> billDetail;

    SearchKeyword searchKeyword;

    ///pharmacy summery all///
    double totalPSCashBV = 0.0;
    double totalPSCashRV = 0.0;
    double totalPSCashNV = 0.0;
    double totalPSCashBC = 0.0;
    double totalPSCashRC = 0.0;
    double totalPSCashNC = 0.0;

    double totalPSCreditBV = 0.0;
    double totalPSCreditRV = 0.0;
    double totalPSCreditNV = 0.0;
    double totalPSCreditBC = 0.0;
    double totalPSCreditRC = 0.0;
    double totalPSCreditNC = 0.0;

    double totalPSCardBV = 0.0;
    double totalPSCardRV = 0.0;
    double totalPSCardNV = 0.0;
    double totalPSCardBC = 0.0;
    double totalPSCardRC = 0.0;
    double totalPSCardNC = 0.0;

    double totalPSSlipBV = 0.0;
    double totalPSSlipRV = 0.0;
    double totalPSSlipNV = 0.0;
    double totalPSSlipBC = 0.0;
    double totalPSSlipRC = 0.0;
    double totalPSSlipNC = 0.0;

    double totalPSChequeBV = 0.0;
    double totalPSChequeRV = 0.0;
    double totalPSChequeNV = 0.0;
    double totalPSChequeBC = 0.0;
    double totalPSChequeRC = 0.0;
    double totalPSChequeNC = 0.0;

    double totalBHTIssueBV = 0.0;
    double totalBHTIssueRV = 0.0;
    double totalBHTIssueNV = 0.0;
    double totalBHTIssueBC = 0.0;
    double totalBHTIssueRC = 0.0;
    double totalBHTIssueNC = 0.0;

    double totalUnitIssueBV = 0.0;
    double totalUnitIssueRV = 0.0;
    double totalUnitIssueNV = 0.0;
    double totalUnitIssueBC = 0.0;
    double totalUnitIssueRC = 0.0;
    double totalUnitIssueNC = 0.0;
    /////

    /////
    @EJB
    private CommonFunctions commonFunctions;
    @EJB
    private BillItemFacade billItemFacade;
    @EJB
    private BillFacade billFacade;

    PaymentScheme paymentScheme;
    PaymentMethod paymentMethod;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void makeNull() {
        fromDate = null;
        category = null;
        toDate = null;
        department = null;
        grantNetTotal = 0;
        grantDiscount = 0;
        grantCardTotal = 0;
        grantCashTotal = 0;
        grantCreditTotal = 0;
        billedSummery = null;
        billedPaymentSummery = null;

    }

    public Department getToDepartment() {
        return toDepartment;
    }

    public void setToDepartment(Department toDepartment) {
        this.toDepartment = toDepartment;
    }

//    private double getSaleValueByDepartment(Date date) {
//        //   List<Stock> billedSummery;
//        Date fd = getCommonFunctions().getStartOfDay(date);
//        Date td = getCommonFunctions().getEndOfDay(date);
//        String sql;
//        Map m = new HashMap();
//        m.put("d", getDepartment());
//        m.put("fd", fd);
//        m.put("td", td);
//        m.put("btp", BillType.PharmacyPre);
//        m.put("refType", BillType.PharmacySale);
//        sql = "select sum(i.netTotal) from Bill i where i.department=:d and i.referenceBill.billType=:refType "
//                + " and i.billType=:btp and i.createdAt between :fd and :td ";
//        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
//        //System.err.println("from " + fromDate);
//        //System.err.println("Sale Value " + saleValue);
//        return saleValue;
//
//    }
    public void createGRNBillItemTable() {
//select bi from BillItem bi where  bi.retired=false  and bi.bill.billType=:bt  and bi.bill.createdAt bettween :fd and :td  and bi.bill.depId like :di  and bi.bill.referenceBill.deptId like :po;
        String sql;
        Map m = new HashMap();
        sql = "select bi from BillItem bi where "
                + " bi.retired=false "
                + " and bi.bill.billType=:bt "
                + " and bi.bill.createdAt between :fd and :td ";

        if (searchKeyword.getBillNo() != null && !searchKeyword.getBillNo().toUpperCase().trim().equals("")) {
            sql += " and (upper(bi.bill.depId) like :di) ";
            m.put("di", "%" + searchKeyword.getBillNo().toUpperCase().trim() + "%");
        }
//        BillItem bi = new BillItem();
//        bi.getBill().getReferenceBill().getDeptId();
//        bi.getBill().getFromInstitution();
        if (searchKeyword.getRefBillNo() != null && !searchKeyword.getRefBillNo().toUpperCase().trim().equals("")) {
            sql += " and (upper(bi.bill.referenceBill.deptId) like :po) ";
            m.put("po", "%" + searchKeyword.getRefBillNo().toUpperCase().trim() + "%");
        }

        if (searchKeyword.getIns() != null) {
            sql += " and bi.bill.fromInstitution=:de ";
            m.put("de", searchKeyword.getIns());
        }

        m.put("bt", BillType.PharmacyGrnBill);
        m.put("fd", getFromDate());
        m.put("td", getToDate());

        billItems = getBillItemFacade().findBySQL(sql, m, TemporalType.TIMESTAMP);
    }

    private double getSaleValueByDepartment(Date date, Bill bill) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netValue) from BillItem i "
                + " where i.bill.referenceBill.department=:d "
                + " and i.retired=false "
                + " and i.bill.billType=:btp "
                + " and type(i.bill)=:cl "
                + " and i.bill.createdAt between :fd and :td ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }

        sql += "  order by i.bill.deptId ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private List<Object[]> fetchSaleValueByDepartment() {
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", PreBill.class);
        m.put("btp", BillType.PharmacySale);
        sql = "select FUNC('Date',i.createdAt),"
                + " i.bill.billClassType,"
                + " sum(i.netValue)"
                + " from BillItem i "
                + "where i.bill.referenceBill.department=:d "
                + " and i.bill.billType=:btp "
                + "and type(i.bill)!=:cl "
                + "and i.bill.createdAt between :fd and :td ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }

        sql += " group by FUNC('Date',i.createdAt),i.bill.billClassType"
                + " order by i.createdAt,i.bill.billClassType ";
        return getBillFacade().findAggregates(sql, m, TemporalType.TIMESTAMP);

    }

    public void createSaleBillItems() {
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", PreBill.class);
        m.put("btp", BillType.PharmacySale);
        sql = "select i "
                + " from BillItem i "
                + "where i.bill.referenceBill.department=:d "
                + " and i.bill.billType=:btp "
                + "and type(i.bill)!=:cl "
                + "and i.bill.createdAt between :fd and :td ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }

        if (item != null) {
            sql += " and i.item=:itm";
            m.put("itm", item);
        }

        sql += "  order by i.item.name,i.createdAt,i.bill.billClassType ";
        billItems = billItemFacade.findBySQL(sql, m, TemporalType.TIMESTAMP);

    }

    private List<Object[]> fetchSaleValueByPaymentmethod() {
        String sql;

        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", PreBill.class);
        m.put("btp", BillType.PharmacySale);
        sql = "select FUNC('Date',i.createdAt),"
                + " i.bill.paymentMethod,"
                + " sum(i.netValue)"
                + " from BillItem i "
                + "where i.bill.referenceBill.department=:d "
                + " and i.bill.billType=:btp "
                + "and type(i.bill)!=:cl "
                + "and i.bill.createdAt between :fd and :td ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }

        sql += " group by FUNC('Date',i.createdAt),i.bill.paymentMethod"
                + " order by i.createdAt,i.bill.paymentMethod ";
        return getBillFacade().findAggregates(sql, m, TemporalType.TIMESTAMP);

    }

    private double getSaleValueByDepartment(Date date) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
//        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.bill.netTotal) "
                + " from BillItem i "
                + "where i.bill.referenceBill.department=:d "
                + " and i.bill.billType=:btp "
                //                + "and type(i.bill)=:cl "
                + "and i.bill.createdAt between :fd and :td ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }
//
//        sql += "  order by i.bill.deptId ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private List<Object[]> getSaleValueByDepartmentCategoryWise() {

        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
//        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select i.item.category,sum(i.netValue) "
                + " from BillItem i "
                + "where i.bill.referenceBill.department=:d "
                + " and i.bill.billType=:btp "
                //                + "and type(i.bill)=:cl "
                + " and i.bill.createdAt between :fd and :td "
                + " group by i.item.category.name"
                + " order by i.item.category.name ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }
//
//        sql += "  order by i.bill.deptId ";
        List<Object[]> saleValue = getBillFacade().findAggregates(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getTransIssueValueByDate(Date date, Bill bill) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacyTransferIssue);
        m.put("tde", getToDepartment());
        sql = "select sum(i.netTotal) from Bill i where i.department=:d and i.toDepartment=:tde"
                + " and i.billType=:btp and type(i)=:cl and i.createdAt between :fd and :td order by i.deptId ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getSaleValueByDepartmentPaymentScheme(Date date, Bill bill) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netTotal) from Bill i where i.referenceBill.department=:d "
                + " and i.billType=:btp and type(i)=:cl and i.createdAt between :fd and :td ";

        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (paymentScheme != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", paymentScheme);

        }

        sql += " order by i.deptId ";

        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }
    
    private double getSaleValueByDepartmentPaymentSchemeP( Bill bill,PaymentScheme ps) {
        
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netTotal) from Bill i where "
                + " i.referenceBill.department=:d "
                + " and i.billType=:btp "
                + " and type(i)=:cl "
                + " and i.createdAt between :fd and :td ";

        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (ps != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", ps);

        }

        sql += " order by i.deptId ";

        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getIssueValueByDepartment(Date date, Bill bill) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacyBhtPre);
        sql = "select sum(i.netTotal) from Bill i where i.referenceBill.department=:d "
                + " and i.billType=:btp and type(i)=:cl and i.createdAt between :fd and :td order by i.deptId ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double calBillFee(Date date, FeeType fTy, PaymentMethod pm) {

        String sql;

        sql = "select sum(f.feeValue) "
                + " from BillFee f "
                + " where f.bill.retired=false "
                + " and f.bill.billType = :billType "
                + " and f.bill.paymentMethod = :pm "
                + " and f.bill.createdAt between :fd and :td "
                + " and f.bill.toInstitution=:ins "
                + " and f.fee.feeType=:ft";

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);

        System.err.println("From " + fd);
        System.err.println("To " + td);

        Map m = new HashMap();
        m.put("fd", fd);
        m.put("td", td);
        m.put("pm", pm);
        m.put("billType", BillType.OpdBill);
        m.put("ins", institution);
        m.put("ft", fTy);
        //    m.put("ins", getSessionController().getInstitution());
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }
    
    private double calBillFee(Date date, FeeType fTy) {

        String sql;

        sql = "select sum(f.feeValue) "
                + " from BillFee f "
                + " where f.bill.retired=false "
                + " and f.bill.billType = :billType "
                + " and (f.bill.paymentMethod = :pm1 "
                + " or f.bill.paymentMethod = :pm2 "
                + " or f.bill.paymentMethod = :pm3 "
                + " or f.bill.paymentMethod = :pm4) "
                + " and f.bill.createdAt between :fd and :td "
                + " and f.bill.toInstitution=:ins "
                + " and f.fee.feeType=:ft";

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);

        System.err.println("From " + fd);
        System.err.println("To " + td);

        Map m = new HashMap();
        m.put("fd", fd);
        m.put("td", td);
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("billType", BillType.OpdBill);
        m.put("ins", institution);
        m.put("ft", fTy);
        //    m.put("ins", getSessionController().getInstitution());
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    @Inject
    private SessionController sessionController;

    private double getHandOverValue(Date date) {

        String sql;
//
//        sql = "select abs(sum(f.total))"
//                + " from Bill f "
//                + " where f.retired=false "
//                + " and f.billType = :billType "
//                + " and type(f) = :class "
//                + " and f.createdAt between :fd and :td "
//                + " and( f.paymentMethod=:pm1"
//                + " or f.paymentMethod=:pm2"
//                + " or f.paymentMethod=:pm3"
//                + " or f.paymentMethod=:pm4 )"
//                + " and f.toInstitution=:ins "
//                + " and f.institution=:billedIns ";
//        
        sql = "select sum(f.total - f.staffFee) "
                + " from Bill f "
                + " where f.retired=false "
                + " and f.billType = :billType "
                + " and (f.paymentMethod = :pm1 "
                + " or f.paymentMethod = :pm2 "
                + " or f.paymentMethod = :pm3 "
                + " or f.paymentMethod = :pm4) "
                + " and f.createdAt between :fd and :td "
                + " and f.toInstitution=:ins ";

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);

        System.err.println("From " + fd);
        System.err.println("To " + td);

        Map m = new HashMap();
        m.put("fd", fd);
        m.put("td", td);
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("billType", BillType.OpdBill);
        m.put("ins", institution);
        //    m.put("ins", getSessionController().getInstitution());
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getHandOverDiscountValue(Date date) {

        String sql;

        sql = "select sum(f.discount)"
                + " from Bill f "
                + " where f.retired=false "
                + " and f.billType = :billType "
                + " and f.createdAt between :fd and :td "
                + " and( f.paymentMethod=:pm1"
                + " or f.paymentMethod=:pm2"
                + " or f.paymentMethod=:pm3"
                + " or f.paymentMethod=:pm4 )"
                + " and f.toInstitution=:ins ";
        //   + " and f.institution=:billedIns ";

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);

        Map m = new HashMap();
        m.put("fd", fd);
        m.put("td", td);
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("billType", BillType.OpdBill);
        m.put("ins", institution);
        // m.put("billedIns", getSessionController().getInstitution());

        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getHandOverProfValue(Date date) {

        String sql;

        sql = "select sum(f.staffFee)"
                + " from Bill f "
                + " where f.retired=false "
                + " and f.billType = :billType "
                + " and f.createdAt between :fd and :td "
                + " and( f.paymentMethod=:pm1"
                + " or f.paymentMethod=:pm2"
                + " or f.paymentMethod=:pm3"
                + " or f.paymentMethod=:pm4 )"
                + " and f.toInstitution=:ins ";
        //      + " and f.institution=:billedIns ";

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);

        Map m = new HashMap();
        m.put("fd", fd);
        m.put("td", td);
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("billType", BillType.OpdBill);
        m.put("ins", institution);
        // m.put("billedIns", getSessionController().getInstitution());

        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getSaleValueByDepartment(Date date, PaymentMethod paymentMethod, Bill bill) {
        //   List<Stock> billedSummery;
        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("pm", paymentMethod);
        m.put("class", bill.getClass());
        //  m.put("btp", BillType.PharmacyPre);
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netTotal) from Bill i where i.paymentMethod=:pm and "
                + " i.referenceBill.department=:d and type(i)=:class "
                + " and i.billType=:btp and i.createdAt between :fd and :td order by i.deptId ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
        //   //System.err.println("from " + fromDate);
        //  //System.err.println("Sale Value " + saleValue);
        return saleValue;

    }

    private double getSaleValuePaymentmethod(Date date, PaymentMethod paymentMethod, Bill bill) {
        //   List<Stock> billedSummery;
        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("pm", paymentMethod);
        m.put("class", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netTotal) from Bill i where type(i)=:class and i.paymentMethod=:pm and "
                + " i.referenceBill.department=:d and i.billType=:btp and i.createdAt between :fd and :td ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }
//
//    private double getDiscountValueByDepartment(Date date) {
//        //   List<Stock> billedSummery;
//        Date fd = getCommonFunctions().getStartOfDay(date);
//        Date td = getCommonFunctions().getEndOfDay(date);
//        String sql;
//        Map m = new HashMap();
//        m.put("d", getDepartment());
//        m.put("fd", fd);
//        m.put("td", td);
//        m.put("btp", BillType.PharmacyPre);
//        m.put("refType", BillType.PharmacySale);
//        sql = "select sum(i.discount) from Bill i where i.department=:d and i.referenceBill.billType=:refType "
//                + " and i.billType=:btp and i.createdAt between :fd and :td ";
//        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
//        //System.err.println("from " + fromDate);
//        //System.err.println("Sale Value " + saleValue);
//        return saleValue;
//
//    }

    private double getDiscountValueByDepartment(Date date, Bill bill) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.discount) from Bill i where "
                + " i.referenceBill.department=:d "
                + " and i.retired=false "
                + " and i.billType=:btp and type(i)=:cl "
                + " and i.createdAt between :fd and :td ";
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private double getDiscountValueByDepartmentPaymentScheme(Date date, Bill bill) {

        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        m.put("cl", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.discount) from Bill i where i.referenceBill.department=:d "
                + " and i.billType=:btp and type(i)=:cl and i.createdAt between :fd and :td ";
        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (paymentScheme != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", paymentScheme);

        }
        double saleValue = getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

        return saleValue;

    }

    private List<Bill> getSaleBillByDepartment(Date date, Bill bill) {
        //   List<Stock> billedSummery;
        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        // m.put("btp", BillType.PharmacyPre);
        m.put("class", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select i from Bill i "
                + " where i.referenceBill.department=:d "
                + " and i.retired=false "
                + " and i.billType=:btp "
                + " and type(i)=:class and "
                + " i.createdAt between :fd and :td "
                + " order by i.deptId ";

        return getBillFacade().findBySQL(sql, m, TemporalType.TIMESTAMP);

    }

    private List<Bill> getSaleBillByDepartmentPaymentScheme(Date date, Bill bill) {
        //   List<Stock> billedSummery;
        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        // m.put("btp", BillType.PharmacyPre);
        m.put("class", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select i from Bill i where i.referenceBill.department=:d  "
                + " and i.billType=:btp and type(i)=:class and"
                + " i.createdAt between :fd and :td ";
        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (paymentScheme != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", paymentScheme);

        }

        sql += " order by i.deptId ";

        return getBillFacade().findBySQL(sql, m, TemporalType.TIMESTAMP);

    }

    private List<Bill> getIssueBillByDepartment(Date date, Bill bill) {
        //   List<Stock> billedSummery;
        Date fd = getCommonFunctions().getStartOfDay(date);
        Date td = getCommonFunctions().getEndOfDay(date);
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", fd);
        m.put("td", td);
        // m.put("btp", BillType.PharmacyPre);
        m.put("class", bill.getClass());
        m.put("btp", BillType.PharmacyBhtPre);
        sql = "select i from Bill i where i.referenceBill.department=:d  "
                + " and i.billType=:btp and type(i)=:class and"
                + " i.createdAt between :fd and :td order by i.deptId ";
        return getBillFacade().findBySQL(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantNetTotalByDepartment() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", PreBill.class);
        m.put("btp", BillType.PharmacySale);

        sql = "select sum(i.netValue) from BillItem i "
                + " where i.bill.referenceBill.department=:d "
                + " and i.retired=false "
                + " and i.bill.billType=:btp "
                + " and type(i.bill)!=:cl ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }

        sql += " and i.bill.createdAt between :fd and :td ";
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantNetTotalIssue() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", PreBill.class);
        m.put("btp", BillType.PharmacyTransferIssue);

        sql = "select sum(i.netTotal) from Bill i where i.department=:d "
                + " and i.billType=:btp and type(i)!=:cl and i.createdAt between :fd and :td ";
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantNetTotalByDepartmentPaymentScheme() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", PreBill.class);
        m.put("btp", BillType.PharmacySale);

        sql = "select sum(i.netTotal) from Bill i where i.referenceBill.department=:d "
                + " and i.billType=:btp and type(i)!=:cl and i.createdAt between :fd and :td ";

        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (paymentScheme != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", paymentScheme);

        }
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calIssueGrantNetTotalByDepartment() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fd", getFromDate());
        m.put("td", getToDate());
        m.put("cl", PreBill.class);
        m.put("btp", BillType.PharmacyPre);

        sql = "select sum(i.netTotal) from Bill i where i.referenceBill.department=:d "
                + " and i.billType=:btp and type(i)!=:cl and i.createdAt between :fd and :td ";
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantNetTotalIssue(Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("class", bill.getClass());
        m.put("tde", getToDepartment());
        // m.put("btp", BillType.PharmacyPre);
        m.put("btp", BillType.PharmacyTransferIssue);
        sql = "select sum(i.netTotal) from Bill i where i.department=:d and i.toDepartment=:tde and"
                + " i.billType=:btp and type(i)=:class "
                + " and i.createdAt between :fromDate and :toDate ";
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantNetTotalByDepartment(Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("class", bill.getClass());
        // m.put("btp", BillType.PharmacyPre);
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netValue)"
                + " from BillItem i "
                + " where i.bill.referenceBill.department=:d "
                + " and i.retired=false "
                + " and i.bill.billType=:btp "
                + " and type(i.bill)=:class ";

        if (category != null) {
            sql += " and i.item.category=:cat ";
            m.put("cat", category);
        }

        sql += " and i.bill.createdAt between :fromDate and :toDate ";
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

//    private double calGrantNetTotalByDepartment() {
//        //   List<Stock> billedSummery;
//        String sql;
//        Map m = new HashMap();
//        m.put("d", getDepartment());
//        m.put("fromDate", getFromDate());
//        m.put("toDate", getToDate());
////        m.put("class", bill.getClass());
//        // m.put("btp", BillType.PharmacyPre);
//        m.put("btp", BillType.PharmacySale);
//        sql = "select sum(i.bill.netTotal)"
//                + " from BillItem i "
//                + " where i.bill.referenceBill.department=:d "
//                + " and i.bill.billType=:btp ";
////                + " and type(i.bill)=:class ";
//
//        if (category != null) {
//            sql += " and i.item.category=:cat ";
//            m.put("cat", category);
//        }
//
//        sql += " and i.bill.createdAt between :fromDate and :toDate ";
//        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
//
//    }
    private double calGrantNetTotalByDepartmentPaymentScheme(Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("class", bill.getClass());
        // m.put("btp", BillType.PharmacyPre);
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netTotal) from Bill i where i.referenceBill.department=:d and"
                + " i.billType=:btp and type(i)=:class "
                + " and i.createdAt between :fromDate and :toDate ";

        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (paymentScheme != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", paymentScheme);

        }

        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calIssueGrantNetTotalByDepartment(Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("class", bill.getClass());
        // m.put("btp", BillType.PharmacyPre);
        m.put("btp", BillType.PharmacyPre);
        sql = "select sum(i.netTotal) from Bill i where i.referenceBill.department=:d and"
                + " i.billType=:btp and type(i)=:class "
                + " and i.createdAt between :fromDate and :toDate ";
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantHandOverNetotal(Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("ins", getInstitution());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("class", bill.getClass());
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("btp", BillType.OpdBill);
        sql = "select sum(abs(i.total) - (abs(i.staffFee) + abs(i.discount))) from "
                + " Bill i where i.toInstitution=:ins "
                + " and i.billType=:btp"
                + " and type(i)=:class "
                + " and( i.paymentMethod=:pm1 "
                + " or i.paymentMethod=:pm2 "
                + " or i.paymentMethod=:pm3 "
                + " or i.paymentMethod=:pm4 )"
                + " and i.createdAt between :fromDate and :toDate ";
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantHandOverNetotal() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("ins", getInstitution());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("btp", BillType.OpdBill);
        sql = "select sum(abs(i.netTotal) - abs(i.staffFee))  "
                + " from Bill i where "
                + " i.toInstitution=:ins "
                + " and i.billType=:btp "
                + " and (i.paymentMethod=:pm1 "
                + " or i.paymentMethod=:pm2 "
                + " or i.paymentMethod=:pm3 "
                + " or i.paymentMethod=:pm4 )"
                + " and i.createdAt between :fromDate and :toDate ";
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantHandOverTotal() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("ins", getInstitution());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("btp", BillType.OpdBill);
        m.put("billedIns", getSessionController().getInstitution());
        sql = "select sum(i.total)  "
                + " from Bill i where "
                + " i.toInstitution=:ins "
                + " and i.institution=:billedIns "
                + " and i.billType=:btp "
                + " and (i.paymentMethod=:pm1 "
                + " or i.paymentMethod=:pm2 "
                + " or i.paymentMethod=:pm3 "
                + " or i.paymentMethod=:pm4 )"
                + " and i.createdAt between :fromDate and :toDate ";
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantHandOverProf() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("ins", getInstitution());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("btp", BillType.OpdBill);
        m.put("billedIns", getSessionController().getInstitution());
        sql = "select sum(i.staffFee)  "
                + " from Bill i where "
                + " i.toInstitution=:ins "
                + " and i.institution=:billedIns "
                + " and i.billType=:btp "
                + " and (i.paymentMethod=:pm1 "
                + " or i.paymentMethod=:pm2 "
                + " or i.paymentMethod=:pm3 "
                + " or i.paymentMethod=:pm4 )"
                + " and i.createdAt between :fromDate and :toDate ";
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantHandOverDiscount() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("ins", getInstitution());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("pm1", PaymentMethod.Cash);
        m.put("pm2", PaymentMethod.Card);
        m.put("pm3", PaymentMethod.Cheque);
        m.put("pm4", PaymentMethod.Slip);
        m.put("btp", BillType.OpdBill);
        m.put("billedIns", getSessionController().getInstitution());
        sql = "select sum(i.discount)  "
                + " from Bill i where "
                + " i.toInstitution=:ins "
                + " and i.institution=:billedIns "
                + " and i.billType=:btp "
                + " and (i.paymentMethod=:pm1 "
                + " or i.paymentMethod=:pm2 "
                + " or i.paymentMethod=:pm3 "
                + " or i.paymentMethod=:pm4 )"
                + " and i.createdAt between :fromDate and :toDate ";
        return getBillFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantTotalByPaymentMethod(PaymentMethod paymentMethod, Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("pm", paymentMethod);
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("class", bill.getClass());
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netTotal) from Bill i where type(i)=:class and i.paymentMethod=:pm and "
                + " i.referenceBill.department=:d and i.billType=:btp and i.createdAt between :fromDate and :toDate ";
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

//    private double calGrantTotalByPaymentMethod(PaymentMethod paymentMethod) {
//        //   List<Stock> billedSummery;
//        String sql;
//        Map m = new HashMap();
//        m.put("d", getDepartment());
//        m.put("pm", paymentMethod);
//        m.put("fromDate", getFromDate());
//        m.put("toDate", getToDate());
//        m.put("btp", BillType.PharmacySale);
//        sql = "select sum(i.netTotal)"
//                + " from Bill i "
//                + " where i.paymentMethod=:pm"
//                + "  and i.referenceBill.department=:d "
//                + " and i.billType=:btp "
//                + " and i.createdAt between :fromDate and :toDate ";
//        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);
//
//    }
    private double calGrantTotalByPaymentMethod(PaymentMethod paymentMethod) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("pm", paymentMethod);
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("class", PreBill.class);
        m.put("btp", BillType.PharmacySale);
        sql = "select sum(i.netValue) "
                + " from BillItem i "
                + " where type(i.bill)!=:class "
                + " and i.bill.paymentMethod=:pm "
                + " and i.bill.referenceBill.department=:d "
                + " and i.bill.billType=:btp "
                + " and i.bill.createdAt between :fromDate and :toDate ";

        if (category != null) {
            sql += " and i.item.category=:cat";
            m.put("cat", category);
        }

        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantDiscountByDepartment(Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("btp", BillType.PharmacySale);
        m.put("class", bill.getClass());
        sql = "select sum(i.discount) from Bill i where type(i)=:class and"
                + " i.referenceBill.department=:d and i.retired=false and  "
                + " i.billType=:btp and i.createdAt between :fromDate and :toDate ";
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantDiscountByDepartmentPaymentScheme(Bill bill) {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("btp", BillType.PharmacySale);
        m.put("class", bill.getClass());
        sql = "select sum(i.discount) from Bill i where type(i)=:class and"
                + " i.referenceBill.department=:d and  "
                + " i.billType=:btp and i.createdAt between :fromDate and :toDate ";

        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (paymentScheme != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", paymentScheme);

        }

        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantDiscountByDepartment() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("btp", BillType.PharmacySale);
        m.put("class", PreBill.class);
        sql = "select sum(i.discount) from Bill i where type(i)!=:class and"
                + " i.referenceBill.department=:d and "
                + " i.retired=false and "
                + " i.billType=:btp and i.createdAt between :fromDate and :toDate ";
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    private double calGrantDiscountByDepartmentPaymentScheme() {
        //   List<Stock> billedSummery;
        String sql;
        Map m = new HashMap();
        m.put("d", getDepartment());
        m.put("fromDate", getFromDate());
        m.put("toDate", getToDate());
        m.put("btp", BillType.PharmacySale);
        m.put("class", PreBill.class);
        sql = "select sum(i.discount) from Bill i where type(i)!=:class and"
                + " i.referenceBill.department=:d and "
                + " i.billType=:btp and i.createdAt between :fromDate and :toDate ";

        if (paymentMethod != null) {

            sql += " and i.paymentMethod=:pm ";
            m.put("pm", paymentMethod);

        }

        if (paymentScheme != null) {

            sql += " and i.paymentScheme=:ps ";
            m.put("ps", paymentScheme);

        }
        return getBillItemFacade().findDoubleByJpql(sql, m, TemporalType.TIMESTAMP);

    }

    public void createSaleReportByDate() {
        billedSummery = new PharmacySummery();

        List<Object[]> list = fetchSaleValueByDepartment();
        TreeMap<Date, String1Value3> hm = new TreeMap<>();

        for (Object[] obj : list) {
            Date date = (Date) obj[0];
            BillClassType billClassType = (BillClassType) obj[1];
            Double value = (Double) obj[2];

            String1Value3 newRow = (String1Value3) hm.get(date);

            if (newRow == null) {
                newRow = new String1Value3();
                newRow.setDate(date);
            } else {
                hm.remove(date);
            }

            switch (billClassType) {
                case BilledBill:
                    newRow.setValue1(value);
                    break;
                case CancelledBill:
                    newRow.setValue2(value);
                    break;
                case RefundBill:
                    newRow.setValue3(value);
                    break;
            }

            hm.put(date, newRow);

        }

//        Collections.s
        List<String1Value3> listRow = new ArrayList<>();
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            listRow.add((String1Value3) pairs.getValue());
//            it.remove(); // avoids a ConcurrentModificationException
        }

        System.err.println(listRow);

        billedSummery.setBills(listRow);

        billedSummery.setBilledTotal(calGrantNetTotalByDepartment(new BilledBill()));
        billedSummery.setCancelledTotal(calGrantNetTotalByDepartment(new CancelledBill()));
        billedSummery.setRefundedTotal(calGrantNetTotalByDepartment(new RefundBill()));

        grantNetTotal = calGrantNetTotalByDepartment();

    }
//    public void createSaleReportByDate() {
//        billedSummery = new PharmacySummery();
//
//        billedSummery.setBills(new ArrayList<String1Value3>());
//
//        Date nowDate = getFromDate();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(nowDate);
//
//        while (nowDate.before(getToDate())) {
//
//            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
//            String formattedDate = df.format(nowDate);
//
//            String1Value3 newRow = new String1Value3();
//            newRow.setString(formattedDate);
//            newRow.setValue1(getSaleValueByDepartment(nowDate, new BilledBill()));
//            newRow.setValue2(getSaleValueByDepartment(nowDate, new CancelledBill()));
//            newRow.setValue3(getSaleValueByDepartment(nowDate, new RefundBill()));
//
//            billedSummery.getBills().add(newRow);
//
//            Calendar nc = Calendar.getInstance();
//            nc.setTime(nowDate);
//            nc.add(Calendar.DATE, 1);
//            nowDate = nc.getTime();
//
//        }
//
//        billedSummery.setBilledTotal(calGrantNetTotalByDepartment(new BilledBill()));
//        billedSummery.setCancelledTotal(calGrantNetTotalByDepartment(new CancelledBill()));
//        billedSummery.setRefundedTotal(calGrantNetTotalByDepartment(new RefundBill()));
//
//        grantNetTotal = calGrantNetTotalByDepartment();
//
//    }

    public void createSaleReportByDate2() {
        billedSummery = new PharmacySummery();

        billedSummery.setBills(new ArrayList<String1Value3>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);

        while (nowDate.before(getToDate())) {

            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String formattedDate = df.format(nowDate);

            String1Value3 newRow = new String1Value3();
            newRow.setString(formattedDate);
            newRow.setValue1(getSaleValueByDepartment(nowDate));

            billedSummery.getBills().add(newRow);

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }
//
//        billedSummery.setBilledTotal(calGrantNetTotalByDepartment(new BilledBill()));
//        billedSummery.setCancelledTotal(calGrantNetTotalByDepartment(new CancelledBill()));
//        billedSummery.setRefundedTotal(calGrantNetTotalByDepartment(new RefundBill()));

        grantNetTotal = calGrantNetTotalByDepartment();

    }

    List<String1Value3> string1Value3s;

    public List<String1Value3> getString1Value3s() {
        return string1Value3s;
    }

    List<CategoryMovementReportRow> categoryMovementReportRows;

    double totalOpdSale;
    double totalInwardIssue;
    double totalDepartmentIssue;
    double totalTatalValue;
    double totalPurchaseValue;
    double totalMargineValue;

    public void createCategoryMovementReport() {
        String jpql;
        Map m = new HashMap();
        PharmaceuticalBillItem pbi = new PharmaceuticalBillItem();
//        pbi.getBillItem().getBill().getCreatedAt();
//        pbi.getBillItem().getBill().getBillType();
//        pbi.getBillItem().getItem();
//        pbi.getPurchaseRate();
//
//        pbi.getBillItem().getBill().getDepartment();
//        pbi.getBillItem().getNetValue();
//        pbi.getItemBatch().getPurcahseRate();
//        pbi.getQty();

        m
                .put("bc", PreBill.class
                );
        m.put(
                "fd", fromDate);
        m.put(
                "td", toDate);
        m.put(
                "cat", category);
        jpql = "select pbi.billItem.bill.billType, pbi.billItem.item, sum(pbi.billItem.netValue), sum(pbi.itemBatch.purcahseRate*pbi.qty) "
                + " from PharmaceuticalBillItem pbi "
                + " where type(pbi.billItem.bill)=:bc "
                + " and pbi.billItem.bill.createdAt between :fd and :td "
                + " and pbi.billItem.item.category=:cat ";
        if (department
                != null) {
            jpql = jpql + " and pbi.billItem.bill.department=:dept ";
            m.put("dept", department);
        }
        jpql = jpql + " group by pbi.billItem.bill.billType, pbi.billItem.item ";
        jpql = jpql + " order by pbi.billItem.item.name ";
        List<Object[]> objs = getBillFacade().findAggregates(jpql, m, TemporalType.TIMESTAMP);
        categoryMovementReportRows = new ArrayList<>();
        Item pi = null;
        CategoryMovementReportRow r;
        r = new CategoryMovementReportRow();
        totalOpdSale = 0.0;
        totalInwardIssue = 0.0;
        totalDepartmentIssue = 0.0;
        totalPurchaseValue = 0.0;
        totalTatalValue = 0.0;
        totalMargineValue = 0.0;
        for (Object o[] : objs) {

            try {

                Item ti;
                BillType tbt;
                double sv;
                double cv;
                ti = (Item) o[1];

                tbt = (BillType) o[0];

                sv = (double) o[2];
                cv = (double) o[3];
                System.out.println("cv = " + cv);
                System.out.println("sv = " + sv);

                System.out.println("pi = " + pi);
                System.out.println("ti = " + ti);

                if (pi == null || !ti.equals(pi)) {
                    System.out.println("new item - " + ti.getName());
                    r = new CategoryMovementReportRow();
                    r.setItem(ti);
                    r.setDepartmentIssue(0.0);
                    r.setInwardIssue(0.0);
                    r.setMarginValue(0.0);
                    r.setOpdSale(0.0);
                    r.setPurchaseValue(0.0);
                    r.setTotal(0.0);
                    r.setTransfer(0.0);
                    r.setTransferIn(0.0);
                    r.setTransferOut(0.0);
                    pi = ti;
                    categoryMovementReportRows.add(r);
                    System.out.println("size = " + categoryMovementReportRows.size());
                }

                System.out.println("tbt = " + tbt);

                switch (tbt) {
                    case PharmacySale:
                    case PharmacyPre:
                        System.out.println("pharmacy sale");
                        System.out.println("r.getOpdSale() = " + r.getOpdSale());
                        r.setOpdSale(r.getOpdSale() + sv);
                        r.setPurchaseValue(r.getPurchaseValue() + cv);
                        break;
                    case PharmacyBhtPre:
                        System.out.println("bht sale ");
                        System.out.println("r.getInwardIssue() = " + r.getInwardIssue());
                        r.setInwardIssue(r.getInwardIssue() + sv);
                        r.setPurchaseValue(r.getPurchaseValue() + cv);
                        System.out.println("r.getInwardIssue() = " + r.getInwardIssue());
                        break;
                    case PharmacyIssue:
                        System.out.println("pharmacy issue ");
                        System.out.println("r.getDepartmentIssue() = " + r.getDepartmentIssue());
                        r.setDepartmentIssue(r.getDepartmentIssue() + sv);
                        r.setPurchaseValue(r.getPurchaseValue() + cv);
                        System.out.println("r.getDepartmentIssue() = " + r.getDepartmentIssue());
                        break;
                    case PharmacyTransferIssue:
                        System.out.println("tx issue ");
                        System.out.println("r.getTransferIn() = " + r.getTransferIn());
                        r.setTransferIn(r.getTransferIn() + sv);
                        System.out.println("r.getTransferIn() = " + r.getTransferIn());
                        break;
                    case PharmacyTransferReceive:
                        System.out.println("tx issue ");
                        System.out.println("r.getTransferOut() = " + r.getTransferOut());
                        r.setTransferOut(r.getTransferOut() + sv);
                        System.out.println("r.getTransferOut() = " + r.getTransferOut());
                        break;

                    default:
                        System.out.println("other bill type");
                }

            } catch (Exception e) {
                System.out.println("e = " + e);
            }

            r.setTotal(r.getOpdSale() + r.getInwardIssue() + r.getDepartmentIssue());
            System.out.println("r.getTotal() = " + r.getTotal());
            r.setMarginValue(r.getTotal() + r.getPurchaseValue());

            totalOpdSale += r.getOpdSale();
            totalInwardIssue += r.getInwardIssue();
            totalDepartmentIssue += r.getDepartmentIssue();
            totalPurchaseValue += r.getPurchaseValue();
            totalTatalValue += r.getTotal();
            totalMargineValue += r.getMarginValue();
        }
    }

    public void createCategoryMovementReportByItemBatch() {
        String jpql;
        Map m = new HashMap();

        m
                .put("bc", PreBill.class
                );
        m.put(
                "fd", fromDate);
        m.put(
                "td", toDate);

        jpql = "select pbi.billItem.bill.billType,"
                + " pbi.itemBatch, "
                + " sum(pbi.billItem.netValue), "
                + " sum(pbi.qty),"
                + " stk"
                + " from PharmaceuticalBillItem pbi,Stock stk "
                + " where type(pbi.billItem.bill)=:bc "
                + " and stk.itemBatch=pbi.itemBatch "
                + " and pbi.billItem.bill.createdAt between :fd and :td ";

        if (category
                != null) {
            jpql = jpql + " and pbi.billItem.item.category=:cat";
            m.put("cat", category);
        }

        if (department
                != null) {
            jpql = jpql + " and pbi.billItem.bill.department=:dept ";
            m.put("dept", department);
        }
        jpql = jpql + " group by pbi.billItem.bill.billType, pbi.itemBatch ";
        jpql = jpql + " order by pbi.itemBatch.item.name ";
        List<Object[]> objs = getBillFacade().findAggregates(jpql, m, TemporalType.TIMESTAMP);
        categoryMovementReportRows = new ArrayList<>();
        ItemBatch pi = null;
        CategoryMovementReportRow r;
        r = new CategoryMovementReportRow();
        totalOpdSale = 0.0;
        totalInwardIssue = 0.0;
        totalDepartmentIssue = 0.0;
        totalPurchaseValue = 0.0;
        totalTatalValue = 0.0;
        totalMargineValue = 0.0;
        for (Object o[] : objs) {

            try {

                ItemBatch itemBatch;
                BillType billType;
                Stock stock;
                double netValue;
                double qty;

                billType = (BillType) o[0];
                itemBatch = (ItemBatch) o[1];
                netValue = (double) o[2];
                qty = (double) o[3];
                stock = (Stock) o[4];

                if (pi == null || !itemBatch.equals(pi)) {
                    r = new CategoryMovementReportRow();
                    r.setItemBatch(itemBatch);
                    r.setStock(stock);
                    r.setDepartmentIssue(0.0);
                    r.setInwardIssue(0.0);
                    r.setMarginValue(0.0);
                    r.setOpdSale(0.0);
                    r.setPurchaseValue(0.0);
                    r.setTotal(0.0);
                    r.setTransfer(0.0);
                    r.setTransferIn(0.0);
                    r.setTransferOut(0.0);
                    pi = itemBatch;
                    categoryMovementReportRows.add(r);
//                    System.out.println("size = " + categoryMovementReportRows.size());
                }

//                System.out.println("tbt = " + tbt);
                switch (billType) {
                    case PharmacySale:
                    case PharmacyPre:
//                        System.out.println("pharmacy sale");
//                        System.out.println("r.getOpdSale() = " + r.getOpdSale());
                        r.setOpdSale(r.getOpdSale() + netValue);
                        r.setOpdSaleQty(qty);
                        r.setPurchaseValue(r.getPurchaseValue() + (itemBatch.getPurcahseRate() * r.getOpdSaleQty()));
                        break;
                    case PharmacyBhtPre:
//                        System.out.println("bht sale ");
//                        System.out.println("r.getInwardIssue() = " + r.getInwardIssue());
                        r.setInwardIssue(r.getInwardIssue() + netValue);
                        r.setInwardIssueQty(qty);
                        r.setPurchaseValue(r.getPurchaseValue() + (itemBatch.getPurcahseRate() * r.getInwardIssueQty()));
//                        System.out.println("r.getInwardIssue() = " + r.getInwardIssue());
                        break;
                    case PharmacyIssue:
//                        System.out.println("pharmacy issue ");
//                        System.out.println("r.getDepartmentIssue() = " + r.getDepartmentIssue());
                        r.setDepartmentIssue(r.getDepartmentIssue() + netValue);
                        r.setDepartmentIssueQty(qty);
                        r.setPurchaseValue(r.getPurchaseValue() + (itemBatch.getPurcahseRate() * r.getDepartmentIssueQty()));
//                        System.out.println("r.getDepartmentIssue() = " + r.getDepartmentIssue());
                        break;
                    case PharmacyTransferIssue:
//                        System.out.println("tx issue ");
//                        System.out.println("r.getTransferIn() = " + r.getTransferIn());
                        r.setTransferIn(r.getTransferIn() + netValue);
//                        System.out.println("r.getTransferIn() = " + r.getTransferIn());
                        break;
                    case PharmacyTransferReceive:
//                        System.out.println("tx issue ");
//                        System.out.println("r.getTransferOut() = " + r.getTransferOut());
                        r.setTransferOut(r.getTransferOut() + netValue);
//                        System.out.println("r.getTransferOut() = " + r.getTransferOut());
                        break;

                    default:
                        System.out.println("other bill type");
                }

            } catch (Exception e) {
                System.out.println("e = " + e);
            }

            r.setTotal(r.getOpdSale() + r.getInwardIssue() + r.getDepartmentIssue());
            System.out.println("r.getTotal() = " + r.getTotal());
            r.setMarginValue(r.getTotal() + r.getPurchaseValue());

            totalOpdSale += r.getOpdSale();
            totalInwardIssue += r.getInwardIssue();
            totalDepartmentIssue += r.getDepartmentIssue();
            totalPurchaseValue += r.getStock().getStock() * r.getItemBatch().getPurcahseRate();
            totalTatalValue += r.getTotal();
            totalMargineValue += r.getMarginValue();
        }
    }

    public List<CategoryMovementReportRow> getCategoryMovementReportRows() {
        return categoryMovementReportRows;
    }

    public void setCategoryMovementReportRows(List<CategoryMovementReportRow> categoryMovementReportRows) {
        this.categoryMovementReportRows = categoryMovementReportRows;
    }

    public void createSaleReportByDate3() {

        List<Object[]> list = getSaleValueByDepartmentCategoryWise();
        string1Value3s = new ArrayList<>();

        for (Object[] obj : list) {
            String1Value3 string1Value3 = new String1Value3();
            string1Value3.setString(((Category) obj[0]).toString());
            string1Value3.setValue1((Double) obj[1]);
            string1Value3s.add(string1Value3);

        }
        grantNetTotal = calGrantNetTotalByDepartment();

    }

    public void createIssueReportByDate() {
        billedSummery = new PharmacySummery();

        billedSummery.setBills(new ArrayList<String1Value3>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);

        while (nowDate.before(getToDate())) {

            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String formattedDate = df.format(nowDate);

            String1Value3 newRow = new String1Value3();
            newRow.setString(formattedDate);
            newRow.setValue1(getTransIssueValueByDate(nowDate, new BilledBill()));
            newRow.setValue2(getTransIssueValueByDate(nowDate, new CancelledBill()));
            newRow.setValue3(getTransIssueValueByDate(nowDate, new RefundBill()));

            billedSummery.getBills().add(newRow);

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedSummery.setBilledTotal(calGrantNetTotalIssue(new BilledBill()));
        billedSummery.setCancelledTotal(calGrantNetTotalIssue(new CancelledBill()));
        billedSummery.setRefundedTotal(calGrantNetTotalIssue(new RefundBill()));

        grantNetTotal = calGrantNetTotalIssue();

    }

    public void createLabHadnOverReportByDate() {
        billedSummery = new PharmacySummery();

        billedSummery.setBills(new ArrayList<String1Value3>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);

        while (nowDate.before(getToDate())) {

            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String formattedDate = df.format(nowDate);

            String1Value3 newRow = new String1Value3();
            newRow.setString(formattedDate);

            double handOverValue = getHandOverValue(nowDate);

            double discount = getHandOverDiscountValue(nowDate);

            double proTot = getHandOverProfValue(nowDate);

            newRow.setValue1(handOverValue + proTot);
            newRow.setValue2(discount);
            newRow.setValue3(proTot);

            billedSummery.getBills().add(newRow);

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedSummery.setBilledTotal(calGrantHandOverTotal());
        billedSummery.setCancelledTotal(calGrantHandOverDiscount());
        billedSummery.setRefundedTotal(calGrantHandOverProf());

    }

    public double calValueSale(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill1, Bill bill2) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp "
                + " and b.paymentMethod=:pm "
                + " and b.referenceBill.department=:dep"
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue(BillType billType, Department department, Bill bill1, Bill bill2) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and (b.department=:dep or b.referenceBill.department=:dep) "
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValueSale(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp "
                + " and b.paymentMethod=:pm "
                + " and b.referenceBill.department=:dep "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    //pasan
    public void createLabReportByDateWithCashAndCredit() {
        billedSummery = new PharmacySummery();

        billedSummery.setBills(new ArrayList<String1Value3>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);

        double hospitalFeeTot = 0.0;
        double profeTotal = 0.0;
        double regentTot = 0.0;
        double hospitalFee;
        double proTot;
        double regentFee;

        while (nowDate.before(getToDate())) {

            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String formattedDate = df.format(nowDate);

            String1Value3 newRow = new String1Value3();
            newRow.setString(formattedDate);

            double hospitalFeeCash = calBillFee(nowDate, FeeType.OwnInstitution, PaymentMethod.Cash);
            double hospitalFeeCredit = calBillFee(nowDate, FeeType.OwnInstitution, PaymentMethod.Credit);
            
            hospitalFee = hospitalFeeCash + hospitalFeeCredit;

            double proTotCash = calBillFee(nowDate, FeeType.Staff, PaymentMethod.Cash);
            double proTotCredit = calBillFee(nowDate, FeeType.Staff, PaymentMethod.Credit);
            
            proTot = proTotCash + proTotCredit;

            double regentFeeCash = calBillFee(nowDate, FeeType.Chemical, PaymentMethod.Cash);
            double regentFeeCredit = calBillFee(nowDate, FeeType.Chemical, PaymentMethod.Credit);
            
            regentFee = regentFeeCash + regentFeeCredit;

            newRow.setValue1(hospitalFee);
            newRow.setValue2(regentFee);
            newRow.setValue3(proTot);

            hospitalFeeTot += hospitalFee;
            profeTotal += proTot;
            regentTot += regentFee;

            billedSummery.getBills().add(newRow);

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedSummery.setBilledTotal(hospitalFeeTot);
        billedSummery.setCancelledTotal(profeTotal);
        billedSummery.setRefundedTotal(regentTot);

    }
    
    public void createLabReportByDate() {
        billedSummery = new PharmacySummery();

        billedSummery.setBills(new ArrayList<String1Value3>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);

        double hospitalFeeTot = 0.0;
        double profeTotal = 0.0;
        double regentTot = 0.0;

        while (nowDate.before(getToDate())) {

            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String formattedDate = df.format(nowDate);

            String1Value3 newRow = new String1Value3();
            newRow.setString(formattedDate);

            double hospitalFee = calBillFee(nowDate, FeeType.OwnInstitution);

            double proTot = calBillFee(nowDate, FeeType.Staff);

            double regentFee = calBillFee(nowDate, FeeType.Chemical);

            newRow.setValue1(hospitalFee);
            newRow.setValue2(regentFee);
            newRow.setValue3(proTot);

            hospitalFeeTot += hospitalFee;
            profeTotal += proTot;
            regentTot += regentFee;

            billedSummery.getBills().add(newRow);

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedSummery.setBilledTotal(hospitalFeeTot);
        billedSummery.setCancelledTotal(profeTotal);
        billedSummery.setRefundedTotal(regentTot);

    }

    public double calValue(BillType billType, Department department, Bill bill) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";

        sql += " and (b.department=:dep or b.referenceBill.department=:dep) "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue(BillType billType, PaymentMethod paymentMethod, Department department) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and b.paymentMethod=:pm ";
        sql += " and (b.department=:dep or b.referenceBill.department=:dep) "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);

        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue(BillType billType, Department department) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and (b.department=:dep or b.referenceBill.department=:dep) "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCountSale(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill1, Bill bill2) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp"
                + " and b.paymentMethod=:pm "
                + " and b.referenceBill.department=:dep "
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCount(BillType billType, Department department, Bill bill1, Bill bill2) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and (b.department=:dep or b.referenceBill.department=:dep) "
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCountSale(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp "
                + " and b.paymentMethod=:pm "
                + " and b.referenceBill.department=:dep "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCount(BillType billType, Department department, Bill bill) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";

        sql += " and (b.department=:dep or b.referenceBill.department=:dep) "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    //Bht issue & Unit issue
    public double calValue2(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill1, Bill bill2) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and b.paymentMethod=:pm ";
        sql += " and b.department=:dep "
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue2(BillType billType, Department department, Bill bill1, Bill bill2) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and b.department=:dep "
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue2(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";

        sql += " and b.paymentMethod=:pm ";

        sql += " and b.department=:dep "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue2(BillType billType, Department department, Bill bill) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";

        sql += " and b.department=:dep "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue2(BillType billType, PaymentMethod paymentMethod, Department department) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and b.paymentMethod=:pm ";
        sql += " and b.department=:dep "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);

        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public double calValue2(BillType billType, Department department) {
        String sql = "Select sum(b.netTotal) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and b.department=:dep "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findDoubleByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCount2(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill1, Bill bill2) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and b.paymentMethod=:pm ";
        sql += " and b.department=:dep "
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCount2(BillType billType, Department department, Bill bill1, Bill bill2) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";
        sql += " and b.department=:dep "
                + " and (type(b)=:class1 "
                + " or type(b)=:class2)"
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill1.getClass());
        hm.put("class2", bill2.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCount2(BillType billType, PaymentMethod paymentMethod, Department department, Bill bill) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";

        sql += " and b.paymentMethod=:pm ";

        sql += " and b.department=:dep "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("pm", paymentMethod);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }

    public long calCount2(BillType billType, Department department, Bill bill) {
        String sql = "Select count(b) "
                + " from Bill b "
                + " where b.retired=false"
                + " and b.billType=:btp ";

        sql += " and b.department=:dep "
                + " and type(b)=:class1 "
                + " and b.createdAt between :fd and :td  ";
        HashMap hm = new HashMap();
        hm.put("btp", billType);
        hm.put("dep", department);
        hm.put("class1", bill.getClass());
        hm.put("fd", getFromDate());
        hm.put("td", getToDate());

        return departmentFacade.findLongByJpql(sql, hm, TemporalType.TIMESTAMP);
    }
    //
    @EJB
    DepartmentFacade departmentFacade;

    public List<Department> fetchDepartment(DepartmentType departmentType) {
        String sql = "Select d from Department d"
                + " where d.retired=false "
                + " and d.departmentType=:dtp ";
        HashMap hm = new HashMap();
        hm.put("dtp", departmentType);
        return departmentFacade.findBySQL(sql, hm);

    }

    public void createPharmacyReport() {
        List<Department> departments = fetchDepartment(DepartmentType.Pharmacy);

        saleValuesCash = new ArrayList<>();
        saleValuesCheque = new ArrayList<>();
        saleValuesSlip = new ArrayList<>();
        saleValuesCard = new ArrayList<>();
        saleValuesCredit = new ArrayList<>();
        bhtIssues = new ArrayList<>();
        unitIssues = new ArrayList<>();

        totalPSCashBV = 0.0;
        totalPSCashRV = 0.0;
        totalPSCashNV = 0.0;
        totalPSCashBC = 0.0;
        totalPSCashRC = 0.0;
        totalPSCashNC = 0.0;

        totalPSCreditBV = 0.0;
        totalPSCreditRV = 0.0;
        totalPSCreditNV = 0.0;
        totalPSCreditBC = 0.0;
        totalPSCreditRC = 0.0;
        totalPSCreditNC = 0.0;

        totalPSCardBV = 0.0;
        totalPSCardRV = 0.0;
        totalPSCardNV = 0.0;
        totalPSCardBC = 0.0;
        totalPSCardRC = 0.0;
        totalPSCardNC = 0.0;

        totalPSSlipBV = 0.0;
        totalPSSlipRV = 0.0;
        totalPSSlipNV = 0.0;
        totalPSSlipBC = 0.0;
        totalPSSlipRC = 0.0;
        totalPSSlipNC = 0.0;

        totalPSChequeBV = 0.0;
        totalPSChequeRV = 0.0;
        totalPSChequeNV = 0.0;
        totalPSChequeBC = 0.0;
        totalPSChequeRC = 0.0;
        totalPSChequeNC = 0.0;

        totalBHTIssueBV = 0.0;
        totalBHTIssueRV = 0.0;
        totalBHTIssueNV = 0.0;
        totalBHTIssueBC = 0.0;
        totalBHTIssueRC = 0.0;
        totalBHTIssueNC = 0.0;

        totalUnitIssueBV = 0.0;
        totalUnitIssueRV = 0.0;
        totalUnitIssueNV = 0.0;
        totalUnitIssueBC = 0.0;
        totalUnitIssueRC = 0.0;
        totalUnitIssueNC = 0.0;

        for (Department dep : departments) {
            String1Value6 newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValueSale(BillType.PharmacySale, PaymentMethod.Cash, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue2(calValueSale(BillType.PharmacySale, PaymentMethod.Cash, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCountSale(BillType.PharmacySale, PaymentMethod.Cash, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue5(calCountSale(BillType.PharmacySale, PaymentMethod.Cash, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5());
            totalPSCashBV += newRow.getValue1();
            totalPSCashRV += newRow.getValue2();
            totalPSCashNV += newRow.getValue3();
            totalPSCashBC += newRow.getValue4();
            totalPSCashRC += newRow.getValue5();
            totalPSCashNC += newRow.getValue6();
            saleValuesCash.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValueSale(BillType.PharmacySale, PaymentMethod.Cheque, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue2(calValueSale(BillType.PharmacySale, PaymentMethod.Cheque, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCountSale(BillType.PharmacySale, PaymentMethod.Cheque, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue5(calCountSale(BillType.PharmacySale, PaymentMethod.Cheque, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5());
            totalPSChequeBV += newRow.getValue1();
            totalPSChequeRV += newRow.getValue2();
            totalPSChequeNV += newRow.getValue3();
            totalPSChequeBC += newRow.getValue4();
            totalPSChequeRC += newRow.getValue5();
            totalPSChequeNC += newRow.getValue6();
            saleValuesCheque.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValueSale(BillType.PharmacySale, PaymentMethod.Slip, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue2(calValueSale(BillType.PharmacySale, PaymentMethod.Slip, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCountSale(BillType.PharmacySale, PaymentMethod.Slip, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue5(calCountSale(BillType.PharmacySale, PaymentMethod.Slip, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5());
            totalPSSlipBV += newRow.getValue1();
            totalPSSlipRV += newRow.getValue2();
            totalPSSlipNV += newRow.getValue3();
            totalPSSlipBC += newRow.getValue4();
            totalPSSlipRC += newRow.getValue5();
            totalPSSlipNC += newRow.getValue6();
            saleValuesSlip.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValueSale(BillType.PharmacySale, PaymentMethod.Card, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue2(calValueSale(BillType.PharmacySale, PaymentMethod.Card, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCountSale(BillType.PharmacySale, PaymentMethod.Card, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue5(calCountSale(BillType.PharmacySale, PaymentMethod.Card, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5());
            totalPSCardBV += newRow.getValue1();
            totalPSCardRV += newRow.getValue2();
            totalPSCardNV += newRow.getValue3();
            totalPSCardBC += newRow.getValue4();
            totalPSCardRC += newRow.getValue5();
            totalPSCardNC += newRow.getValue6();
            saleValuesCard.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValueSale(BillType.PharmacySale, PaymentMethod.Credit, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue2(calValueSale(BillType.PharmacySale, PaymentMethod.Credit, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCountSale(BillType.PharmacySale, PaymentMethod.Credit, dep, new BilledBill(), new CancelledBill()));
            newRow.setValue5(calCountSale(BillType.PharmacySale, PaymentMethod.Credit, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5());
            totalPSCreditBV += newRow.getValue1();
            totalPSCreditRV += newRow.getValue2();
            totalPSCreditNV += newRow.getValue3();
            totalPSCreditBC += newRow.getValue4();
            totalPSCreditRC += newRow.getValue5();
            totalPSCreditNC += newRow.getValue6();
            saleValuesCredit.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValue2(BillType.PharmacyBhtPre, dep, new PreBill(), new CancelledBill()));
            newRow.setValue2(calValue2(BillType.PharmacyBhtPre, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCount2(BillType.PharmacyBhtPre, dep, new PreBill(), new CancelledBill()));
            newRow.setValue5(calCount2(BillType.PharmacyBhtPre, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5());
            totalBHTIssueBV += newRow.getValue1();
            totalBHTIssueRV += newRow.getValue2();
            totalBHTIssueNV += newRow.getValue3();
            totalBHTIssueBC += newRow.getValue4();
            totalBHTIssueRC += newRow.getValue5();
            totalBHTIssueNC += newRow.getValue6();
            bhtIssues.add(newRow);

            ////////////
            newRow = new String1Value6();
            newRow.setString(dep.getName());
            newRow.setValue1(calValue2(BillType.PharmacyIssue, dep, new PreBill(), new CancelledBill()));
            newRow.setValue2(calValue2(BillType.PharmacyIssue, dep, new RefundBill()));
            newRow.setValue3(newRow.getValue1() + newRow.getValue2());
            newRow.setValue4(calCount2(BillType.PharmacyIssue, dep, new PreBill(), new CancelledBill()));
            newRow.setValue5(calCount2(BillType.PharmacyIssue, dep, new RefundBill()));
            newRow.setValue6(newRow.getValue4() - newRow.getValue5());
            totalUnitIssueBV += newRow.getValue1();
            totalUnitIssueRV += newRow.getValue2();
            totalUnitIssueNV += newRow.getValue3();
            totalUnitIssueBC += newRow.getValue4();
            totalUnitIssueRC += newRow.getValue5();
            totalUnitIssueNC += newRow.getValue6();
            unitIssues.add(newRow);
        }

    }

//       public void createPharmacyReport() {
//        List<Department> departments = fetchDepartment(DepartmentType.Pharmacy);
//
//        saleValuesCash = new ArrayList<>();
//        saleValuesCheque = new ArrayList<>();
//        saleValuesSlip = new ArrayList<>();
//        saleValuesCard = new ArrayList<>();
//        saleValuesCredit = new ArrayList<>();
//        bhtIssues = new ArrayList<>();
//        unitIssues = new ArrayList<>();
//
//        totalPSCashBV = 0.0;
//        totalPSCashRV = 0.0;
//        totalPSCashNV = 0.0;
//        totalPSCashBC = 0.0;
//        totalPSCashRC = 0.0;
//        totalPSCashNC = 0.0;
//
//        totalPSCreditBV = 0.0;
//        totalPSCreditRV = 0.0;
//        totalPSCreditNV = 0.0;
//        totalPSCreditBC = 0.0;
//        totalPSCreditRC = 0.0;
//        totalPSCreditNC = 0.0;
//
//        totalPSCardBV = 0.0;
//        totalPSCardRV = 0.0;
//        totalPSCardNV = 0.0;
//        totalPSCardBC = 0.0;
//        totalPSCardRC = 0.0;
//        totalPSCardNC = 0.0;
//
//        totalPSSlipBV = 0.0;
//        totalPSSlipRV = 0.0;
//        totalPSSlipNV = 0.0;
//        totalPSSlipBC = 0.0;
//        totalPSSlipRC = 0.0;
//        totalPSSlipNC = 0.0;
//
//        totalPSChequeBV = 0.0;
//        totalPSChequeRV = 0.0;
//        totalPSChequeNV = 0.0;
//        totalPSChequeBC = 0.0;
//        totalPSChequeRC = 0.0;
//        totalPSChequeNC = 0.0;
//
//        totalBHTIssueBV = 0.0;
//        totalBHTIssueRV = 0.0;
//        totalBHTIssueNV = 0.0;
//        totalBHTIssueBC = 0.0;
//        totalBHTIssueRC = 0.0;
//        totalBHTIssueNC = 0.0;
//
//        totalUnitIssueBV = 0.0;
//        totalUnitIssueRV = 0.0;
//        totalUnitIssueNV = 0.0;
//        totalUnitIssueBC = 0.0;
//        totalUnitIssueRC = 0.0;
//        totalUnitIssueNC = 0.0;
//
//        for (Department dep : departments) {
//            String1Value6 newRow = new String1Value6();
//            newRow.setString(dep.getName());
//            newRow.setValue1(calValue(BillType.PharmacySale, PaymentMethod.Cash, dep, new BilledBill(), new CancelledBill()));
//            newRow.setValue2(calValue(BillType.PharmacySale, PaymentMethod.Cash, dep, new RefundBill()));
//            newRow.setValue3(calValue(BillType.PharmacySale, PaymentMethod.Cash, dep));
//            newRow.setValue4(calCount(BillType.PharmacySale, PaymentMethod.Cash, dep, new BilledBill(), new CancelledBill()));
//            newRow.setValue5(calCount(BillType.PharmacySale, PaymentMethod.Cash, dep, new RefundBill()));
//            newRow.setValue6(newRow.getValue4() - newRow.getValue5());
//            totalPSCashBV += newRow.getValue1();
//            totalPSCashRV += newRow.getValue2();
//            totalPSCashNV += newRow.getValue3();
//            totalPSCashBC += newRow.getValue4();
//            totalPSCashRC += newRow.getValue5();
//            totalPSCashNC += newRow.getValue6();
//            saleValuesCash.add(newRow);
//
//            ////////////
//            newRow = new String1Value6();
//            newRow.setString(dep.getName());
//            newRow.setValue1(calValue(BillType.PharmacySale, PaymentMethod.Cheque, dep, new BilledBill(), new CancelledBill()));
//            newRow.setValue2(calValue(BillType.PharmacySale, PaymentMethod.Cheque, dep, new RefundBill()));
//            newRow.setValue3(calValue(BillType.PharmacySale, PaymentMethod.Cheque, dep));
//            newRow.setValue4(calCount(BillType.PharmacySale, PaymentMethod.Cheque, dep, new BilledBill(), new CancelledBill()));
//            newRow.setValue5(calCount(BillType.PharmacySale, PaymentMethod.Cheque, dep, new RefundBill()));
//            newRow.setValue6(newRow.getValue4() - newRow.getValue5());
//            totalPSChequeBV += newRow.getValue1();
//            totalPSChequeRV += newRow.getValue2();
//            totalPSChequeNV += newRow.getValue3();
//            totalPSChequeBC += newRow.getValue4();
//            totalPSChequeRC += newRow.getValue5();
//            totalPSChequeNC += newRow.getValue6();
//            saleValuesCheque.add(newRow);
//
//            ////////////
//            newRow = new String1Value6();
//            newRow.setString(dep.getName());
//            newRow.setValue1(calValue(BillType.PharmacySale, PaymentMethod.Slip, dep, new BilledBill(), new CancelledBill()));
//            newRow.setValue2(calValue(BillType.PharmacySale, PaymentMethod.Slip, dep, new RefundBill()));
//            newRow.setValue3(calValue(BillType.PharmacySale, PaymentMethod.Slip, dep));
//            newRow.setValue4(calCount(BillType.PharmacySale, PaymentMethod.Slip, dep, new BilledBill(), new CancelledBill()));
//            newRow.setValue5(calCount(BillType.PharmacySale, PaymentMethod.Slip, dep, new RefundBill()));
//            newRow.setValue6(newRow.getValue4() - newRow.getValue5());
//            totalPSSlipBV += newRow.getValue1();
//            totalPSSlipRV += newRow.getValue2();
//            totalPSSlipNV += newRow.getValue3();
//            totalPSSlipBC += newRow.getValue4();
//            totalPSSlipRC += newRow.getValue5();
//            totalPSSlipNC += newRow.getValue6();
//            saleValuesSlip.add(newRow);
//
//            ////////////
//            newRow = new String1Value6();
//            newRow.setString(dep.getName());
//            newRow.setValue1(calValue(BillType.PharmacySale, PaymentMethod.Card, dep, new BilledBill(), new CancelledBill()));
//            newRow.setValue2(calValue(BillType.PharmacySale, PaymentMethod.Card, dep, new RefundBill()));
//            newRow.setValue3(calValue(BillType.PharmacySale, PaymentMethod.Card, dep));
//            newRow.setValue4(calCount(BillType.PharmacySale, PaymentMethod.Card, dep, new BilledBill(), new CancelledBill()));
//            newRow.setValue5(calCount(BillType.PharmacySale, PaymentMethod.Card, dep, new RefundBill()));
//            newRow.setValue6(newRow.getValue4() - newRow.getValue5());
//            totalPSCardBV += newRow.getValue1();
//            totalPSCardRV += newRow.getValue2();
//            totalPSCardNV += newRow.getValue3();
//            totalPSCardBC += newRow.getValue4();
//            totalPSCardRC += newRow.getValue5();
//            totalPSCardNC += newRow.getValue6();
//            saleValuesCard.add(newRow);
//
//            ////////////
//            newRow = new String1Value6();
//            newRow.setString(dep.getName());
//            newRow.setValue1(calValue(BillType.PharmacySale, PaymentMethod.Credit, dep, new BilledBill(), new CancelledBill()));
//            newRow.setValue2(calValue(BillType.PharmacySale, PaymentMethod.Credit, dep, new RefundBill()));
//            newRow.setValue3(calValue(BillType.PharmacySale, PaymentMethod.Credit, dep));
//            newRow.setValue4(calCount(BillType.PharmacySale, PaymentMethod.Credit, dep, new BilledBill(), new CancelledBill()));
//            newRow.setValue5(calCount(BillType.PharmacySale, PaymentMethod.Credit, dep, new RefundBill()));
//            newRow.setValue6(newRow.getValue4() - newRow.getValue5());
//            totalPSCreditBV += newRow.getValue1();
//            totalPSCreditRV += newRow.getValue2();
//            totalPSCreditNV += newRow.getValue3();
//            totalPSCreditBC += newRow.getValue4();
//            totalPSCreditRC += newRow.getValue5();
//            totalPSCreditNC += newRow.getValue6();
//            saleValuesCredit.add(newRow);
//
//            ////////////
//            newRow = new String1Value6();
//            newRow.setString(dep.getName());
//            newRow.setValue1(calValue2(BillType.PharmacyBhtPre, dep, new PreBill(), new CancelledBill()));
//            newRow.setValue2(calValue2(BillType.PharmacyBhtPre, dep, new RefundBill()));
//            newRow.setValue3(calValue2(BillType.PharmacyBhtPre, dep));
//            newRow.setValue4(calCount2(BillType.PharmacyBhtPre, dep, new PreBill(), new CancelledBill()));
//            newRow.setValue5(calCount2(BillType.PharmacyBhtPre, dep, new RefundBill()));
//            newRow.setValue6(newRow.getValue4() - newRow.getValue5());
//            totalBHTIssueBV += newRow.getValue1();
//            totalBHTIssueRV += newRow.getValue2();
//            totalBHTIssueNV += newRow.getValue3();
//            totalBHTIssueBC += newRow.getValue4();
//            totalBHTIssueRC += newRow.getValue5();
//            totalBHTIssueNC += newRow.getValue6();
//            bhtIssues.add(newRow);
//
//            ////////////
//            newRow = new String1Value6();
//            newRow.setString(dep.getName());
//            newRow.setValue1(calValue2(BillType.PharmacyIssue, dep, new PreBill(), new CancelledBill()));
//            newRow.setValue2(calValue2(BillType.PharmacyIssue, dep, new RefundBill()));
//            newRow.setValue3(calValue2(BillType.PharmacyIssue, dep));
//            newRow.setValue4(calCount2(BillType.PharmacyIssue, dep, new PreBill(), new CancelledBill()));
//            newRow.setValue5(calCount2(BillType.PharmacyIssue, dep, new RefundBill()));
//            newRow.setValue6(newRow.getValue4() - newRow.getValue5());
//            totalUnitIssueBV += newRow.getValue1();
//            totalUnitIssueRV += newRow.getValue2();
//            totalUnitIssueNV += newRow.getValue3();
//            totalUnitIssueBC += newRow.getValue4();
//            totalUnitIssueRC += newRow.getValue5();
//            totalUnitIssueNC += newRow.getValue6();
//            unitIssues.add(newRow);
//        }
//
//    }
    public void createSalePaymentMethod() {
        billedPaymentSummery = new PharmacyPaymetMethodSummery();

        List<Object[]> list = fetchSaleValueByPaymentmethod();
        TreeMap<Date, String2Value4> hm = new TreeMap<>();

        for (Object[] obj : list) {
            Date date = (Date) obj[0];
            PaymentMethod pm = (PaymentMethod) obj[1];
            Double value = (Double) obj[2];

            String2Value4 newRow = (String2Value4) hm.get(date);

            if (newRow == null) {
                newRow = new String2Value4();
                newRow.setDate(date);
            } else {
                hm.remove(date);
            }

            switch (pm) {
                case Cash:
                    newRow.setValue1(value);
                    break;
                case Credit:
                    newRow.setValue2(value);
                    break;
                case Card:
                    newRow.setValue3(value);
                    break;
            }

            hm.put(date, newRow);

        }

        List<String2Value4> listRow = new ArrayList<>();
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            listRow.add((String2Value4) pairs.getValue());
//            it.remove(); // avoids a ConcurrentModificationException
        }

        System.err.println(listRow);

        billedPaymentSummery.setBills(listRow);

        billedPaymentSummery.setCashTotal(calGrantTotalByPaymentMethod(PaymentMethod.Cash));

        ////////////
        billedPaymentSummery.setCreditTotal(calGrantTotalByPaymentMethod(PaymentMethod.Credit));

        ////////////////
        billedPaymentSummery.setCardTotal(calGrantTotalByPaymentMethod(PaymentMethod.Card));

        grantCardTotal = calGrantTotalByPaymentMethod(PaymentMethod.Card);
        grantCashTotal = calGrantTotalByPaymentMethod(PaymentMethod.Cash);
        grantCreditTotal = calGrantTotalByPaymentMethod(PaymentMethod.Credit);

    }

//    public void createSalePaymentMethod() {
//        billedPaymentSummery = new PharmacyPaymetMethodSummery();
//        billedPaymentSummery.setBills(new ArrayList<String2Value4>());
//
//        Date nowDate = getFromDate();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(nowDate);
//
//        while (nowDate.before(getToDate())) {
//
//            String2Value4 newRow = new String2Value4();
//
//            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
//            String formattedDate = df.format(nowDate);
//
//            newRow.setString(formattedDate);
//
//            double cash = 0;
//            double credit = 0;
//            double card = 0;
//
//            ////////
//            cash = getSaleValuePaymentmethod(nowDate, PaymentMethod.Cash, new BilledBill());
//            cash += getSaleValuePaymentmethod(nowDate, PaymentMethod.Cash, new CancelledBill());
//            cash += getSaleValuePaymentmethod(nowDate, PaymentMethod.Cash, new RefundBill());
//            /////////////
//            credit = getSaleValuePaymentmethod(nowDate, PaymentMethod.Credit, new BilledBill());
//            credit += getSaleValuePaymentmethod(nowDate, PaymentMethod.Credit, new CancelledBill());
//            credit += getSaleValuePaymentmethod(nowDate, PaymentMethod.Credit, new RefundBill());
//
//            //////////////
//            card = getSaleValuePaymentmethod(nowDate, PaymentMethod.Card, new BilledBill());
//            card += getSaleValuePaymentmethod(nowDate, PaymentMethod.Card, new CancelledBill());
//            card += getSaleValuePaymentmethod(nowDate, PaymentMethod.Card, new RefundBill());
//
//            newRow.setValue1(cash);
//            newRow.setValue2(credit);
//            newRow.setValue3(card);
//            newRow.setValue4(cash + credit + card);
//
//            billedPaymentSummery.getBills().add(newRow);
//
//            Calendar nc = Calendar.getInstance();
//            nc.setTime(nowDate);
//            nc.add(Calendar.DATE, 1);
//            nowDate = nc.getTime();
//
//        }
//
//        billedPaymentSummery.setCashTotal(
//                calGrantTotalByPaymentMethod(PaymentMethod.Cash, new BilledBill())
//                + calGrantTotalByPaymentMethod(PaymentMethod.Cash, new CancelledBill())
//                + calGrantTotalByPaymentMethod(PaymentMethod.Cash, new RefundBill()));
//
//        ////////////
//        billedPaymentSummery.setCreditTotal(
//                calGrantTotalByPaymentMethod(PaymentMethod.Credit, new BilledBill())
//                + calGrantTotalByPaymentMethod(PaymentMethod.Credit, new CancelledBill())
//                + calGrantTotalByPaymentMethod(PaymentMethod.Credit, new RefundBill()));
//
//        ////////////////
//        billedPaymentSummery.setCardTotal(
//                calGrantTotalByPaymentMethod(PaymentMethod.Card, new BilledBill())
//                + calGrantTotalByPaymentMethod(PaymentMethod.Card, new CancelledBill())
//                + calGrantTotalByPaymentMethod(PaymentMethod.Card, new RefundBill()));
//
//        grantCardTotal = calGrantTotalByPaymentMethod(PaymentMethod.Card);
//        grantCashTotal = calGrantTotalByPaymentMethod(PaymentMethod.Cash);
//        grantCreditTotal = calGrantTotalByPaymentMethod(PaymentMethod.Credit);
//    }
    public void createSaleReportByDateDetail() {
        billedDetail = new PharmacyDetail();
        cancelledDetail = new PharmacyDetail();
        refundedDetail = new PharmacyDetail();

        billedDetail.setDatedBills(new ArrayList<DatedBills>());
        cancelledDetail.setDatedBills(new ArrayList<DatedBills>());
        refundedDetail.setDatedBills(new ArrayList<DatedBills>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        while (nowDate.before(getToDate())) {

            double sumNetToal = getSaleValueByDepartment(nowDate, new BilledBill());
            double sumDiscount = getDiscountValueByDepartment(nowDate, new BilledBill());
            DatedBills newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartment(nowDate, new BilledBill()));

            if (!newRow.getBills().isEmpty()) {
                billedDetail.getDatedBills().add(newRow);
            }

            sumNetToal = getSaleValueByDepartment(nowDate, new CancelledBill());
            sumDiscount = getDiscountValueByDepartment(nowDate, new CancelledBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartment(nowDate, new CancelledBill()));

            if (!newRow.getBills().isEmpty()) {
                cancelledDetail.getDatedBills().add(newRow);
            }

            sumNetToal = getSaleValueByDepartment(nowDate, new RefundBill());
            sumDiscount = getDiscountValueByDepartment(nowDate, new RefundBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartment(nowDate, new RefundBill()));

            if (!newRow.getBills().isEmpty()) {
                refundedDetail.getDatedBills().add(newRow);
            }

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedDetail.setNetTotal(calGrantNetTotalByDepartment(new BilledBill()));
        billedDetail.setDiscount(calGrantDiscountByDepartment(new BilledBill()));

        cancelledDetail.setNetTotal(calGrantNetTotalByDepartment(new CancelledBill()));
        cancelledDetail.setDiscount(calGrantDiscountByDepartment(new CancelledBill()));

        refundedDetail.setNetTotal(calGrantNetTotalByDepartment(new RefundBill()));
        refundedDetail.setDiscount(calGrantDiscountByDepartment(new RefundBill()));

        grantNetTotal = calGrantNetTotalByDepartment();
        grantDiscount = calGrantDiscountByDepartment();

    }

    public void createSaleReportByDateDetailPaymentScheme() {
        billedDetail = new PharmacyDetail();
        cancelledDetail = new PharmacyDetail();
        refundedDetail = new PharmacyDetail();

        billedDetail.setDatedBills(new ArrayList<DatedBills>());
        cancelledDetail.setDatedBills(new ArrayList<DatedBills>());
        refundedDetail.setDatedBills(new ArrayList<DatedBills>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        while (nowDate.before(getToDate())) {

            double sumNetToal = getSaleValueByDepartmentPaymentScheme(nowDate, new BilledBill());
            double sumDiscount = getDiscountValueByDepartmentPaymentScheme(nowDate, new BilledBill());
            DatedBills newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartmentPaymentScheme(nowDate, new BilledBill()));

            if (!newRow.getBills().isEmpty()) {
                billedDetail.getDatedBills().add(newRow);
            }

            sumNetToal = getSaleValueByDepartmentPaymentScheme(nowDate, new CancelledBill());
            sumDiscount = getDiscountValueByDepartmentPaymentScheme(nowDate, new CancelledBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartmentPaymentScheme(nowDate, new CancelledBill()));

            if (!newRow.getBills().isEmpty()) {
                cancelledDetail.getDatedBills().add(newRow);
            }

            sumNetToal = getSaleValueByDepartmentPaymentScheme(nowDate, new RefundBill());
            sumDiscount = getDiscountValueByDepartmentPaymentScheme(nowDate, new RefundBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartmentPaymentScheme(nowDate, new RefundBill()));

            if (!newRow.getBills().isEmpty()) {
                refundedDetail.getDatedBills().add(newRow);
            }

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedDetail.setNetTotal(calGrantNetTotalByDepartmentPaymentScheme(new BilledBill()));
        billedDetail.setDiscount(calGrantDiscountByDepartmentPaymentScheme(new BilledBill()));

        cancelledDetail.setNetTotal(calGrantNetTotalByDepartmentPaymentScheme(new CancelledBill()));
        cancelledDetail.setDiscount(calGrantDiscountByDepartmentPaymentScheme(new CancelledBill()));

        refundedDetail.setNetTotal(calGrantNetTotalByDepartmentPaymentScheme(new RefundBill()));
        refundedDetail.setDiscount(calGrantDiscountByDepartmentPaymentScheme(new RefundBill()));

        grantNetTotal = calGrantNetTotalByDepartmentPaymentScheme();
        grantDiscount = calGrantDiscountByDepartmentPaymentScheme();

    }
    
    List<PaymentSchemeSummery> paymentSchemeSummerys;
    double billTotal;
    double canTotal;
    double refTotal;
    
    @Inject
    PaymentSchemeController paymentSchemeController;
    
    public List<PaymentSchemeSummery> getPaymentSchemeSummerys() {
        return paymentSchemeSummerys;
    }

    public void setPaymentSchemeSummerys(List<PaymentSchemeSummery> paymentSchemeSummerys) {
        this.paymentSchemeSummerys = paymentSchemeSummerys;
    }

    public double getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(double billTotal) {
        this.billTotal = billTotal;
    }

    public double getCanTotal() {
        return canTotal;
    }

    public void setCanTotal(double canTotal) {
        this.canTotal = canTotal;
    }

    public double getRefTotal() {
        return refTotal;
    }

    public void setRefTotal(double refTotal) {
        this.refTotal = refTotal;
    }
    
    
    
    public void createSalereportByDateSummeryPaymentscheam(){
        if (department==null) {
            UtilityController.addErrorMessage("Select Department");
            return;
        }
        paymentSchemeSummerys=new ArrayList<>();
        List<PaymentScheme> paymentSchemes=paymentSchemeController.getItems();
        
        billTotal=0.0;
        canTotal=0.0;
        refTotal=0.0;
        
        for (PaymentScheme ps : paymentSchemes) {
            PaymentSchemeSummery pss=new PaymentSchemeSummery();
            
            pss.setPaymentScheme(ps.getName());
            pss.setBillTotal(getSaleValueByDepartmentPaymentSchemeP(new BilledBill(), ps));
            pss.setCancelBillTotal(getSaleValueByDepartmentPaymentSchemeP(new CancelledBill(), ps));
            pss.setRefundBillTotal(getSaleValueByDepartmentPaymentSchemeP(new RefundBill(), ps));
            
            billTotal+=pss.getBillTotal();
            canTotal+=pss.getCancelBillTotal();
            refTotal+=pss.getRefundBillTotal();
            
            paymentSchemeSummerys.add(pss);
            System.out.println("Added - "+ps.getName());
        }
        
    }
    
    public class PaymentSchemeSummery{
        String paymentScheme;
        double billTotal;
        double cancelBillTotal;
        double refundBillTotal;

        public String getPaymentScheme() {
            return paymentScheme;
        }

        public void setPaymentScheme(String paymentScheme) {
            this.paymentScheme = paymentScheme;
        }

        public double getBillTotal() {
            return billTotal;
        }

        public void setBillTotal(double billTotal) {
            this.billTotal = billTotal;
        }

        public double getCancelBillTotal() {
            return cancelBillTotal;
        }

        public void setCancelBillTotal(double cancelBillTotal) {
            this.cancelBillTotal = cancelBillTotal;
        }

        public double getRefundBillTotal() {
            return refundBillTotal;
        }

        public void setRefundBillTotal(double refundBillTotal) {
            this.refundBillTotal = refundBillTotal;
        }
        
        
        
    }

    public void createIssueReportByDateDetail() {
        billedDetail = new PharmacyDetail();
        cancelledDetail = new PharmacyDetail();
        refundedDetail = new PharmacyDetail();

        billedDetail.setDatedBills(new ArrayList<DatedBills>());
        cancelledDetail.setDatedBills(new ArrayList<DatedBills>());
        refundedDetail.setDatedBills(new ArrayList<DatedBills>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        while (nowDate.before(getToDate())) {

            double sumNetToal = getIssueValueByDepartment(nowDate, new PreBill());
//            double sumDiscount = getDiscountValueByDepartment(nowDate, new BilledBill());
            DatedBills newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
//            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getIssueBillByDepartment(nowDate, new PreBill()));

            if (!newRow.getBills().isEmpty()) {
                billedDetail.getDatedBills().add(newRow);
            }

            sumNetToal = getIssueValueByDepartment(nowDate, new CancelledBill());
//            sumDiscount = getDiscountValueByDepartment(nowDate, new CancelledBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
//            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getIssueBillByDepartment(nowDate, new CancelledBill()));

            if (!newRow.getBills().isEmpty()) {
                cancelledDetail.getDatedBills().add(newRow);
            }

            sumNetToal = getIssueValueByDepartment(nowDate, new RefundBill());
//            sumDiscount = getDiscountValueByDepartment(nowDate, new RefundBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumNetTotal(sumNetToal);
//            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getIssueBillByDepartment(nowDate, new RefundBill()));

            if (!newRow.getBills().isEmpty()) {
                refundedDetail.getDatedBills().add(newRow);
            }

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedDetail.setNetTotal(calIssueGrantNetTotalByDepartment(new PreBill()));
//        billedDetail.setDiscount(calGrantDiscountByDepartment(new BilledBill()));

        cancelledDetail.setNetTotal(calIssueGrantNetTotalByDepartment(new CancelledBill()));
//        cancelledDetail.setDiscount(calGrantDiscountByDepartment(new CancelledBill()));

        refundedDetail.setNetTotal(calIssueGrantNetTotalByDepartment(new RefundBill()));
//        refundedDetail.setDiscount(calGrantDiscountByDepartment(new RefundBill()));

        grantNetTotal = calIssueGrantNetTotalByDepartment();
//        grantDiscount = calGrantDiscountByDepartment();

    }

    public void createSalePaymentMethodDetail() {
        billedDetail = new PharmacyDetail();
        cancelledDetail = new PharmacyDetail();
        refundedDetail = new PharmacyDetail();

        billedDetail.setDatedBills(new ArrayList<DatedBills>());
        cancelledDetail.setDatedBills(new ArrayList<DatedBills>());
        refundedDetail.setDatedBills(new ArrayList<DatedBills>());

        Date nowDate = getFromDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        while (nowDate.before(getToDate())) {

            double sumCash = getSaleValueByDepartment(nowDate, PaymentMethod.Cash, new BilledBill());
            double sumCredit = getSaleValueByDepartment(nowDate, PaymentMethod.Credit, new BilledBill());
            double sumCard = getSaleValueByDepartment(nowDate, PaymentMethod.Card, new BilledBill());
            double sumDiscount = getDiscountValueByDepartment(nowDate, new BilledBill());
            DatedBills newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumCashTotal(sumCash);
            newRow.setSumCreditTotal(sumCredit);
            newRow.setSumCardTotal(sumCard);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartment(nowDate, new BilledBill()));

            if (!newRow.getBills().isEmpty()) {
                billedDetail.getDatedBills().add(newRow);
            }

            ///
            sumCash = getSaleValueByDepartment(nowDate, PaymentMethod.Cash, new CancelledBill());
            sumCredit = getSaleValueByDepartment(nowDate, PaymentMethod.Credit, new CancelledBill());
            sumCard = getSaleValueByDepartment(nowDate, PaymentMethod.Card, new CancelledBill());
            sumDiscount = getDiscountValueByDepartment(nowDate, new CancelledBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumCashTotal(sumCash);
            newRow.setSumCreditTotal(sumCredit);
            newRow.setSumCardTotal(sumCard);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartment(nowDate, new CancelledBill()));

            if (!newRow.getBills().isEmpty()) {
                cancelledDetail.getDatedBills().add(newRow);
            }

            ///
            sumCash = getSaleValueByDepartment(nowDate, PaymentMethod.Cash, new RefundBill());
            sumCredit = getSaleValueByDepartment(nowDate, PaymentMethod.Credit, new RefundBill());
            sumCard = getSaleValueByDepartment(nowDate, PaymentMethod.Card, new RefundBill());
            sumDiscount = getDiscountValueByDepartment(nowDate, new RefundBill());
            newRow = new DatedBills();
            newRow.setDate(nowDate);
            newRow.setSumCashTotal(sumCash);
            newRow.setSumCreditTotal(sumCredit);
            newRow.setSumCardTotal(sumCard);
            newRow.setSumDiscount(sumDiscount);
            newRow.setBills(getSaleBillByDepartment(nowDate, new RefundBill()));

            if (!newRow.getBills().isEmpty()) {
                refundedDetail.getDatedBills().add(newRow);
            }

            Calendar nc = Calendar.getInstance();
            nc.setTime(nowDate);
            nc.add(Calendar.DATE, 1);
            nowDate = nc.getTime();

        }

        billedDetail.setDiscount(calGrantDiscountByDepartment(new BilledBill()));
        billedDetail.setCashTotal(calGrantTotalByPaymentMethod(PaymentMethod.Cash, new BilledBill()));
        billedDetail.setCreditTotal(calGrantTotalByPaymentMethod(PaymentMethod.Credit, new BilledBill()));
        billedDetail.setCardTotal(calGrantTotalByPaymentMethod(PaymentMethod.Card, new BilledBill()));

        cancelledDetail.setDiscount(calGrantDiscountByDepartment(new CancelledBill()));
        cancelledDetail.setCashTotal(calGrantTotalByPaymentMethod(PaymentMethod.Cash, new CancelledBill()));
        cancelledDetail.setCreditTotal(calGrantTotalByPaymentMethod(PaymentMethod.Credit, new CancelledBill()));
        cancelledDetail.setCardTotal(calGrantTotalByPaymentMethod(PaymentMethod.Card, new CancelledBill()));

        refundedDetail.setDiscount(calGrantDiscountByDepartment(new RefundBill()));
        refundedDetail.setCashTotal(calGrantTotalByPaymentMethod(PaymentMethod.Cash, new RefundBill()));
        refundedDetail.setCreditTotal(calGrantTotalByPaymentMethod(PaymentMethod.Credit, new RefundBill()));
        refundedDetail.setCardTotal(calGrantTotalByPaymentMethod(PaymentMethod.Card, new RefundBill()));

        grantCardTotal = 0;
        grantCashTotal = 0;
        grantCreditTotal = 0;
        grantDiscount = 0;
        grantCardTotal = grantCardTotal + calGrantTotalByPaymentMethod(PaymentMethod.Card);
        grantCashTotal = grantCashTotal + calGrantTotalByPaymentMethod(PaymentMethod.Cash);
        grantCreditTotal = grantCreditTotal + calGrantTotalByPaymentMethod(PaymentMethod.Credit);
        grantDiscount = grantDiscount + calGrantDiscountByDepartment();

    }

    /**
     * Creates a new instance of PharmacySaleReport
     */
    public PharmacySaleReport() {
    }

    public Date getFromDate() {
        if (fromDate == null) {
            
            fromDate = getCommonFunctions().getStartOfMonth(new Date());
        }
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        if (toDate == null) {
            toDate = getCommonFunctions().getEndOfMonth(new Date());
        }
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public CommonFunctions getCommonFunctions() {
        return commonFunctions;
    }

    public void setCommonFunctions(CommonFunctions commonFunctions) {
        this.commonFunctions = commonFunctions;
    }

    public BillItemFacade getBillItemFacade() {
        return billItemFacade;
    }

    public void setBillItemFacade(BillItemFacade billItemFacade) {
        this.billItemFacade = billItemFacade;
    }

    public double getGrantNetTotal() {
        return grantNetTotal;
    }

    public void setGrantNetTotal(double grantNetTotal) {
        this.grantNetTotal = grantNetTotal;
    }

    public BillFacade getBillFacade() {
        return billFacade;
    }

    public void setBillFacade(BillFacade billFacade) {
        this.billFacade = billFacade;
    }

    public double getGrantDiscount() {
        return grantDiscount;
    }

    public void setGrantDiscount(double grantDiscount) {
        this.grantDiscount = grantDiscount;
    }

    public PharmacySummery getBilledSummery() {
        return billedSummery;
    }

    public void setBilledSummery(PharmacySummery billedSummery) {
        this.billedSummery = billedSummery;
    }

    public PharmacyPaymetMethodSummery getBilledPaymentSummery() {
        return billedPaymentSummery;
    }

    public void setBilledPaymentSummery(PharmacyPaymetMethodSummery billedPaymentSummery) {
        this.billedPaymentSummery = billedPaymentSummery;
    }

    public double getGrantCashTotal() {
        return grantCashTotal;
    }

    public void setGrantCashTotal(double grantCashTotal) {
        this.grantCashTotal = grantCashTotal;
    }

    public double getGrantCreditTotal() {
        return grantCreditTotal;
    }

    public void setGrantCreditTotal(double grantCreditTotal) {
        this.grantCreditTotal = grantCreditTotal;
    }

    public double getGrantCardTotal() {
        return grantCardTotal;
    }

    public void setGrantCardTotal(double grantCardTotal) {
        this.grantCardTotal = grantCardTotal;
    }

    public PharmacyDetail getBilledDetail() {
        return billedDetail;
    }

    public void setBilledDetail(PharmacyDetail billedDetail) {
        this.billedDetail = billedDetail;
    }

    public PharmacyDetail getCancelledDetail() {
        return cancelledDetail;
    }

    public void setCancelledDetail(PharmacyDetail cancelledDetail) {
        this.cancelledDetail = cancelledDetail;
    }

    public PharmacyDetail getRefundedDetail() {
        return refundedDetail;
    }

    public void setRefundedDetail(PharmacyDetail refundedDetail) {
        this.refundedDetail = refundedDetail;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public double getGrantTotal() {
        return grantTotal;
    }

    public void setGrantTotal(double grantTotal) {
        this.grantTotal = grantTotal;
    }

    public double getGrantProfessional() {
        return grantProfessional;
    }

    public void setGrantProfessional(double grantProfessional) {
        this.grantProfessional = grantProfessional;
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public PaymentScheme getPaymentScheme() {
        return paymentScheme;
    }

    public void setPaymentScheme(PaymentScheme paymentScheme) {
        this.paymentScheme = paymentScheme;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<String1Value6> getSaleValuesCash() {
        return saleValuesCash;
    }

    public void setSaleValuesCash(List<String1Value6> saleValuesCash) {
        this.saleValuesCash = saleValuesCash;
    }

    public List<String1Value6> getSaleValuesCheque() {
        return saleValuesCheque;
    }

    public void setSaleValuesCheque(List<String1Value6> saleValuesCheque) {
        this.saleValuesCheque = saleValuesCheque;
    }

    public DepartmentFacade getDepartmentFacade() {
        return departmentFacade;
    }

    public void setDepartmentFacade(DepartmentFacade departmentFacade) {
        this.departmentFacade = departmentFacade;
    }

    public List<String1Value6> getSaleValuesSlip() {
        return saleValuesSlip;
    }

    public void setSaleValuesSlip(List<String1Value6> saleValuesSlip) {
        this.saleValuesSlip = saleValuesSlip;
    }

    public List<String1Value6> getSaleValuesCard() {
        return saleValuesCard;
    }

    public void setSaleValuesCard(List<String1Value6> saleValuesCard) {
        this.saleValuesCard = saleValuesCard;
    }

    public List<String1Value6> getSaleValuesCredit() {
        return saleValuesCredit;
    }

    public void setSaleValuesCredit(List<String1Value6> saleValuesCredit) {
        this.saleValuesCredit = saleValuesCredit;
    }

    public List<String1Value6> getBhtIssues() {
        return bhtIssues;
    }

    public void setBhtIssues(List<String1Value6> bhtIssues) {
        this.bhtIssues = bhtIssues;
    }

    public List<String1Value6> getUnitIssues() {
        return unitIssues;
    }

    public void setUnitIssues(List<String1Value6> unitIssues) {
        this.unitIssues = unitIssues;
    }

    public double getTotalPSCashBV() {
        return totalPSCashBV;
    }

    public void setTotalPSCashBV(double totalPSCashBV) {
        this.totalPSCashBV = totalPSCashBV;
    }

    public double getTotalPSCashRV() {
        return totalPSCashRV;
    }

    public void setTotalPSCashRV(double totalPSCashRV) {
        this.totalPSCashRV = totalPSCashRV;
    }

    public double getTotalPSCashNV() {
        return totalPSCashNV;
    }

    public void setTotalPSCashNV(double totalPSCashNV) {
        this.totalPSCashNV = totalPSCashNV;
    }

    public double getTotalPSCashBC() {
        return totalPSCashBC;
    }

    public void setTotalPSCashBC(double totalPSCashBC) {
        this.totalPSCashBC = totalPSCashBC;
    }

    public double getTotalPSCashRC() {
        return totalPSCashRC;
    }

    public void setTotalPSCashRC(double totalPSCashRC) {
        this.totalPSCashRC = totalPSCashRC;
    }

    public double getTotalPSCashNC() {
        return totalPSCashNC;
    }

    public void setTotalPSCashNC(double totalPSCashNC) {
        this.totalPSCashNC = totalPSCashNC;
    }

    public double getTotalPSCreditBV() {
        return totalPSCreditBV;
    }

    public void setTotalPSCreditBV(double totalPSCreditBV) {
        this.totalPSCreditBV = totalPSCreditBV;
    }

    public double getTotalPSCreditRV() {
        return totalPSCreditRV;
    }

    public void setTotalPSCreditRV(double totalPSCreditRV) {
        this.totalPSCreditRV = totalPSCreditRV;
    }

    public double getTotalPSCreditNV() {
        return totalPSCreditNV;
    }

    public void setTotalPSCreditNV(double totalPSCreditNV) {
        this.totalPSCreditNV = totalPSCreditNV;
    }

    public double getTotalPSCreditBC() {
        return totalPSCreditBC;
    }

    public void setTotalPSCreditBC(double totalPSCreditBC) {
        this.totalPSCreditBC = totalPSCreditBC;
    }

    public double getTotalPSCreditRC() {
        return totalPSCreditRC;
    }

    public void setTotalPSCreditRC(double totalPSCreditRC) {
        this.totalPSCreditRC = totalPSCreditRC;
    }

    public double getTotalPSCreditNC() {
        return totalPSCreditNC;
    }

    public void setTotalPSCreditNC(double totalPSCreditNC) {
        this.totalPSCreditNC = totalPSCreditNC;
    }

    public double getTotalPSCardBV() {
        return totalPSCardBV;
    }

    public void setTotalPSCardBV(double totalPSCardBV) {
        this.totalPSCardBV = totalPSCardBV;
    }

    public double getTotalPSCardRV() {
        return totalPSCardRV;
    }

    public void setTotalPSCardRV(double totalPSCardRV) {
        this.totalPSCardRV = totalPSCardRV;
    }

    public double getTotalPSCardNV() {
        return totalPSCardNV;
    }

    public void setTotalPSCardNV(double totalPSCardNV) {
        this.totalPSCardNV = totalPSCardNV;
    }

    public double getTotalPSCardBC() {
        return totalPSCardBC;
    }

    public void setTotalPSCardBC(double totalPSCardBC) {
        this.totalPSCardBC = totalPSCardBC;
    }

    public double getTotalPSCardRC() {
        return totalPSCardRC;
    }

    public void setTotalPSCardRC(double totalPSCardRC) {
        this.totalPSCardRC = totalPSCardRC;
    }

    public double getTotalPSCardNC() {
        return totalPSCardNC;
    }

    public void setTotalPSCardNC(double totalPSCardNC) {
        this.totalPSCardNC = totalPSCardNC;
    }

    public double getTotalPSSlipBV() {
        return totalPSSlipBV;
    }

    public void setTotalPSSlipBV(double totalPSSlipBV) {
        this.totalPSSlipBV = totalPSSlipBV;
    }

    public double getTotalPSSlipRV() {
        return totalPSSlipRV;
    }

    public void setTotalPSSlipRV(double totalPSSlipRV) {
        this.totalPSSlipRV = totalPSSlipRV;
    }

    public double getTotalPSSlipNV() {
        return totalPSSlipNV;
    }

    public void setTotalPSSlipNV(double totalPSSlipNV) {
        this.totalPSSlipNV = totalPSSlipNV;
    }

    public double getTotalPSSlipBC() {
        return totalPSSlipBC;
    }

    public void setTotalPSSlipBC(double totalPSSlipBC) {
        this.totalPSSlipBC = totalPSSlipBC;
    }

    public double getTotalPSSlipRC() {
        return totalPSSlipRC;
    }

    public void setTotalPSSlipRC(double totalPSSlipRC) {
        this.totalPSSlipRC = totalPSSlipRC;
    }

    public double getTotalPSSlipNC() {
        return totalPSSlipNC;
    }

    public void setTotalPSSlipNC(double totalPSSlipNC) {
        this.totalPSSlipNC = totalPSSlipNC;
    }

    public double getTotalPSChequeBV() {
        return totalPSChequeBV;
    }

    public void setTotalPSChequeBV(double totalPSChequeBV) {
        this.totalPSChequeBV = totalPSChequeBV;
    }

    public double getTotalPSChequeRV() {
        return totalPSChequeRV;
    }

    public void setTotalPSChequeRV(double totalPSChequeRV) {
        this.totalPSChequeRV = totalPSChequeRV;
    }

    public double getTotalPSChequeNV() {
        return totalPSChequeNV;
    }

    public void setTotalPSChequeNV(double totalPSChequeNV) {
        this.totalPSChequeNV = totalPSChequeNV;
    }

    public double getTotalPSChequeBC() {
        return totalPSChequeBC;
    }

    public void setTotalPSChequeBC(double totalPSChequeBC) {
        this.totalPSChequeBC = totalPSChequeBC;
    }

    public double getTotalPSChequeRC() {
        return totalPSChequeRC;
    }

    public void setTotalPSChequeRC(double totalPSChequeRC) {
        this.totalPSChequeRC = totalPSChequeRC;
    }

    public double getTotalPSChequeNC() {
        return totalPSChequeNC;
    }

    public void setTotalPSChequeNC(double totalPSChequeNC) {
        this.totalPSChequeNC = totalPSChequeNC;
    }

    public double getTotalBHTIssueBV() {
        return totalBHTIssueBV;
    }

    public void setTotalBHTIssueBV(double totalBHTIssueBV) {
        this.totalBHTIssueBV = totalBHTIssueBV;
    }

    public double getTotalBHTIssueRV() {
        return totalBHTIssueRV;
    }

    public void setTotalBHTIssueRV(double totalBHTIssueRV) {
        this.totalBHTIssueRV = totalBHTIssueRV;
    }

    public double getTotalBHTIssueNV() {
        return totalBHTIssueNV;
    }

    public void setTotalBHTIssueNV(double totalBHTIssueNV) {
        this.totalBHTIssueNV = totalBHTIssueNV;
    }

    public double getTotalBHTIssueBC() {
        return totalBHTIssueBC;
    }

    public void setTotalBHTIssueBC(double totalBHTIssueBC) {
        this.totalBHTIssueBC = totalBHTIssueBC;
    }

    public double getTotalBHTIssueRC() {
        return totalBHTIssueRC;
    }

    public void setTotalBHTIssueRC(double totalBHTIssueRC) {
        this.totalBHTIssueRC = totalBHTIssueRC;
    }

    public double getTotalBHTIssueNC() {
        return totalBHTIssueNC;
    }

    public void setTotalBHTIssueNC(double totalBHTIssueNC) {
        this.totalBHTIssueNC = totalBHTIssueNC;
    }

    public double getTotalUnitIssueBV() {
        return totalUnitIssueBV;
    }

    public void setTotalUnitIssueBV(double totalUnitIssueBV) {
        this.totalUnitIssueBV = totalUnitIssueBV;
    }

    public double getTotalUnitIssueRV() {
        return totalUnitIssueRV;
    }

    public void setTotalUnitIssueRV(double totalUnitIssueRV) {
        this.totalUnitIssueRV = totalUnitIssueRV;
    }

    public double getTotalUnitIssueNV() {
        return totalUnitIssueNV;
    }

    public void setTotalUnitIssueNV(double totalUnitIssueNV) {
        this.totalUnitIssueNV = totalUnitIssueNV;
    }

    public double getTotalUnitIssueBC() {
        return totalUnitIssueBC;
    }

    public void setTotalUnitIssueBC(double totalUnitIssueBC) {
        this.totalUnitIssueBC = totalUnitIssueBC;
    }

    public double getTotalUnitIssueRC() {
        return totalUnitIssueRC;
    }

    public void setTotalUnitIssueRC(double totalUnitIssueRC) {
        this.totalUnitIssueRC = totalUnitIssueRC;
    }

    public double getTotalUnitIssueNC() {
        return totalUnitIssueNC;
    }

    public void setTotalUnitIssueNC(double totalUnitIssueNC) {
        this.totalUnitIssueNC = totalUnitIssueNC;
    }

    public double getTotalOpdSale() {
        return totalOpdSale;
    }

    public void setTotalOpdSale(double totalOpdSale) {
        this.totalOpdSale = totalOpdSale;
    }

    public double getTotalInwardIssue() {
        return totalInwardIssue;
    }

    public void setTotalInwardIssue(double totalInwardIssue) {
        this.totalInwardIssue = totalInwardIssue;
    }

    public double getTotalDepartmentIssue() {
        return totalDepartmentIssue;
    }

    public void setTotalDepartmentIssue(double totalDepartmentIssue) {
        this.totalDepartmentIssue = totalDepartmentIssue;
    }

    public double getTotalTatalValue() {
        return totalTatalValue;
    }

    public void setTotalTatalValue(double totalTatalValue) {
        this.totalTatalValue = totalTatalValue;
    }

    public double getTotalPurchaseValue() {
        return totalPurchaseValue;
    }

    public void setTotalPurchaseValue(double totalPurchaseValue) {
        this.totalPurchaseValue = totalPurchaseValue;
    }

    public double getTotalMargineValue() {
        return totalMargineValue;
    }

    public void setTotalMargineValue(double totalMargineValue) {
        this.totalMargineValue = totalMargineValue;
    }

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }

    public SearchKeyword getSearchKeyword() {
        if (searchKeyword == null) {
            searchKeyword = new SearchKeyword();
        }
        return searchKeyword;
    }

    public void setSearchKeyword(SearchKeyword searchKeyword) {
        this.searchKeyword = searchKeyword;

    }

    public class CategoryMovementReportRow {

        Item item;
        ItemBatch itemBatch;
        Stock stock;
        double opdSale;
        double opdSaleQty;
        double inwardIssue;
        double inwardIssueQty;
        double departmentIssue;
        double departmentIssueQty;
        double total;
        double purchaseValue;
        double marginValue;
        double transfer;
        double transferIn;
        double transferInQty;
        double transferOut;
        double transferOutQty;

        public Stock getStock() {
            return stock;
        }

        public void setStock(Stock stock) {
            this.stock = stock;
        }

        public ItemBatch getItemBatch() {
            return itemBatch;
        }

        public void setItemBatch(ItemBatch itemBatch) {
            this.itemBatch = itemBatch;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public double getOpdSale() {
            return opdSale;
        }

        public void setOpdSale(double opdSale) {
            this.opdSale = opdSale;
        }

        public double getInwardIssue() {
            return inwardIssue;
        }

        public void setInwardIssue(double inwardIssue) {
            this.inwardIssue = inwardIssue;
        }

        public double getDepartmentIssue() {
            return departmentIssue;
        }

        public void setDepartmentIssue(double departmentIssue) {
            this.departmentIssue = departmentIssue;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public double getPurchaseValue() {
            return purchaseValue;
        }

        public void setPurchaseValue(double purchaseValue) {
            this.purchaseValue = purchaseValue;
        }

        public double getMarginValue() {
            return marginValue;
        }

        public void setMarginValue(double marginValue) {
            this.marginValue = marginValue;
        }

        public double getTransfer() {
            return transfer;
        }

        public void setTransfer(double transfer) {
            this.transfer = transfer;
        }

        public double getTransferIn() {
            return transferIn;
        }

        public void setTransferIn(double transferIn) {
            this.transferIn = transferIn;
        }

        public double getTransferOut() {
            return transferOut;
        }

        public void setTransferOut(double transferOut) {
            this.transferOut = transferOut;
        }

        public double getOpdSaleQty() {
            return opdSaleQty;
        }

        public void setOpdSaleQty(double opdSaleQty) {
            this.opdSaleQty = opdSaleQty;
        }

        public double getInwardIssueQty() {
            return inwardIssueQty;
        }

        public void setInwardIssueQty(double inwardIssueQty) {
            this.inwardIssueQty = inwardIssueQty;
        }

        public double getDepartmentIssueQty() {
            return departmentIssueQty;
        }

        public void setDepartmentIssueQty(double departmentIssueQty) {
            this.departmentIssueQty = departmentIssueQty;
        }

        public double getTransferInQty() {
            return transferInQty;
        }

        public void setTransferInQty(double transferInQty) {
            this.transferInQty = transferInQty;
        }

        public double getTransferOutQty() {
            return transferOutQty;
        }

        public void setTransferOutQty(double transferOutQty) {
            this.transferOutQty = transferOutQty;
        }

    }

}
