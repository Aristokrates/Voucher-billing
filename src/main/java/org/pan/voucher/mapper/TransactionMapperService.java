package org.pan.voucher.mapper;

import java.util.LinkedList;
import java.util.List;

import org.pan.voucher.model.BoughtVoucher;
import org.pan.voucher.model.Transaction;
import org.pan.voucher.rest.model.TransactionRestModel;
import org.pan.voucher.rest.model.VoucherRestModel;

public class TransactionMapperService {

	public List<TransactionRestModel> buildTransactionListModel(List<Transaction> transactions) {
		
		List<TransactionRestModel> txnRestModel = new LinkedList<TransactionRestModel>();
		
		for (Transaction txn : transactions) {
			TransactionRestModel model = new TransactionRestModel();
			model.setAmmount(txn.getAmount());
			model.setBalance(txn.getBalance());
			model.setCredit(txn.getCredit());
			model.setDateCreated(txn.getDateCreated());
			model.setDateModified(txn.getLastModifiedDate());
			model.setDebit(txn.getDebit());
			model.setMobileNumber(txn.getMobileNumber());
			model.setTransactionType(txn.getTransationType().toString());
			
			if (!txn.getBoughtVouchers().isEmpty()) {
				for (BoughtVoucher voucher : txn.getBoughtVouchers()) {
					model.getVouchers().add(new VoucherRestModel(false, "", voucher.getPin(), voucher.getSerial(), voucher.getVendor() == null ? null : voucher.getVendor().getName()));
				}
			}
			
			txnRestModel.add(model);
		}
		return txnRestModel;
	}
}
