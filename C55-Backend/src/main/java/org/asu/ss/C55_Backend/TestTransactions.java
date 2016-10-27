package org.asu.ss.C55_Backend;

import org.asu.ss.model.TempTransaction;
import org.asu.ss.service.ExtService;


public class TestTransactions 
{

	public static void main(String args[])
	{
		ExtService testtransactions =  new  ExtService();
		TempTransaction temtrans = new TempTransaction();
		
		temtrans.setCust_id(3);
		temtrans.setEmail("puranamsrinivas26@gmail.com");
		temtrans.setItem("Email");
		String FieldtobeUpdated = temtrans.getItem();
		
		String result = testtransactions.updateOTPTransactionsTable(temtrans, FieldtobeUpdated);
		
		System.out.print("updateOTPTransactionsTable completed.");
	}
}
