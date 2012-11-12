package org.pan.voucher.remoting;

import java.util.List;

import org.pan.voucher.exception.VoucherNotFoundException;
import org.pan.voucher.remoting.model.Voucher;

public interface VoucherRemoteService {
	
	Voucher getVoucher(String network, Integer sellValue) throws VoucherNotFoundException;
	
	List<Voucher> getVouchers(String network, Integer voucherId, Integer count) throws VoucherNotFoundException;

}
