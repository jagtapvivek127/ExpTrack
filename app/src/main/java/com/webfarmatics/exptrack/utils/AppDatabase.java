package com.webfarmatics.exptrack.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.webfarmatics.exptrack.bean.BeanItemStates;
import com.webfarmatics.exptrack.bean.BeanLoan;
import com.webfarmatics.exptrack.bean.BeanLoanHelper;
import com.webfarmatics.exptrack.bean.BeanPurchase;
import com.webfarmatics.exptrack.bean.BeanSplit;
import com.webfarmatics.exptrack.bean.BeanSplitFinalHelper;
import com.webfarmatics.exptrack.bean.BeanSplitHelper;
import com.webfarmatics.exptrack.bean.BeanStatesByDate;
import com.webfarmatics.exptrack.bean.BeanStatesByDateFinalHelper;
import com.webfarmatics.exptrack.bean.BeanThought;
import com.webfarmatics.exptrack.bean.BeanTransactions;
import com.webfarmatics.exptrack.bean.BeanTransactionsHelper;

import java.util.ArrayList;


public class AppDatabase extends SQLiteAssetHelper {

    int count = 1;

    private SQLiteDatabase database;
    private static final String DB_NAME = "dailyexp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String BANK_TABLE = "tbl_bank";
    //columns   id,balance

    private static final String ITEM_TABLE = "tbl_item";
    //columns   itemId,itemName

    private static final String WALLET_TABLE = "tbl_wallet";
    //columns   id,balance

    private static final String TRANS_STATS_TABLE = "tbl_trans_stats";
    //columns   id,type,noOfTrans,moneySpend,priority

    private static final String TRANSACTIONS_TABLE = "tbl_transactions";
    //columns   id,item,amount,desc,date

    private static final String LOAN_TABLE = "tbl_loan";
    //columns   id,tofrom,amount,date,desc,type

    private static final String TRANS_TYPE_BY_DATE = "tbl_trans_stats_bydate";
    //columns id,type,moneySpend,priority,date

    private static final String SPLIT_TABLE = "tbl_split";
    //columns id,title,amount,date,comment,participants,owes

    private static final String LOGIN_TABLE = "tbl_login";
    //columns name,password,nickname

    public AppDatabase(Context context) {

        super(context, DB_NAME, null, DATABASE_VERSION);

    }


    private static AppDatabase mInstance = null;

    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppDatabase(context);
        }
        return mInstance;
    }

    public boolean saveLoginDetails(String userName, String password, String nickname) {

        SQLiteDatabase database = getWritableDatabase();

        long count = 0;

        ContentValues values = new ContentValues();
        //columns   id,item,amount,desc,date,paymentBy
        values.put("name", userName);
        values.put("password", password);
        values.put("nickname", nickname);

        count = database.insert(LOGIN_TABLE, null, values);

        if (count > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkLogin(String name, String password) {

        logMessage("db", "else " + name);
        logMessage("db", "else " + password);

        SQLiteDatabase database = getReadableDatabase();
        String columns[] = {"name", "password"};
        Cursor cursor = database.query(LOGIN_TABLE, columns, null, null, null, null, null);

        String name1 = null, password1 = null;

        int count = cursor.getCount();

        if (count > 0) {
            cursor.moveToFirst();
            do {
                name1 = cursor.getString(0);
                password1 = cursor.getString(1);
                logMessage("db", name1);
                logMessage("db", password1);
            } while (cursor.moveToNext());
            logMessage("db", "if " + name1);
            logMessage("db", "if " + password1);
        } else {
            logMessage("db", "else " + name1);
            logMessage("db", "else " + password1);
        }

        cursor.close();

        if (name.equalsIgnoreCase(name1) && password.equalsIgnoreCase(password1)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkNickname(String nickname) {

        SQLiteDatabase database = getReadableDatabase();
        String columns[] = {"nickname"};
        Cursor cursor = database.query(LOGIN_TABLE, columns, null, null, null, null, null);

        String nickname1 = null;

        int count = cursor.getCount();

        if (count > 0) {
            cursor.moveToFirst();
            do {
                nickname1 = cursor.getString(0);
                logMessage("db", nickname1);
            } while (cursor.moveToNext());
            logMessage("db", "if " + nickname1);
        } else {
            logMessage("db", "else " + nickname1);
        }

        cursor.close();

        if (nickname.equalsIgnoreCase(nickname1)) {
            return true;
        } else {
            return false;
        }

    }

    public String getBankBalance() {

        SQLiteDatabase database = getReadableDatabase();
        String columns[] = {"balance"};
        Cursor cursor = database.query(BANK_TABLE, columns, null, null, null, null, null);

        String balance = null;

        int count = cursor.getCount();

        if (count > 0) {
            cursor.moveToFirst();
            do {
                balance = cursor.getString(0);
                logMessage("db", balance);
            } while (cursor.moveToNext());
            logMessage("db", "if " + balance);
        } else {
            logMessage("db", "else " + balance);
        }

        cursor.close();

        return balance;

    }

    public String getWalletBalance() {

        SQLiteDatabase database = getReadableDatabase();
        String columns[] = {"balance"};
        Cursor cursor = database.query(WALLET_TABLE, columns, null, null, null, null, null);

        String balance = null;

        int count = cursor.getCount();

        if (count > 0) {
            cursor.moveToFirst();
            do {
                balance = cursor.getString(0);
                logMessage("db", balance);
            } while (cursor.moveToNext());
            logMessage("db", "if " + balance);
        } else {
            logMessage("db", "else " + balance);
        }


        cursor.close();

        return balance;

    }

    public void logMessage(String tag, String s) {
        Log.e(tag, "-- " + s + " --");
    }

    public boolean reduceBankBal(int amount) {

        SQLiteDatabase database = getReadableDatabase();

        String columns[] = {"id", "balance"};

        Cursor cursor = database.query(BANK_TABLE, columns, null, null, null, null, null);

        String bankBal = null, id = null;

        int count = cursor.getCount();

        if (count > 0) {
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                bankBal = cursor.getString(1);
                logMessage("db", bankBal);
            } while (cursor.moveToNext());
            logMessage("db", "if " + bankBal);
        } else {
            logMessage("db", "else " + bankBal);
        }

        logMessage("banck bal ", bankBal);


        int oldBal = Integer.parseInt(bankBal);
        int newBal = oldBal - amount;

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("balance", newBal);
        String whereClause = "id=?";
        String whereArgs[] = {id};
        int cnt = db.update(BANK_TABLE, contentValues, whereClause, whereArgs);

        cursor.close();

        if (cnt > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean reduceWalletBal(int amount) {

        SQLiteDatabase database = getReadableDatabase();
        String columns[] = {"id", "balance"};
        Cursor cursor = database.query(WALLET_TABLE, columns, null, null, null, null, null);

        String bankBal = null, id = null;

        int count = cursor.getCount();

        if (count > 0) {
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                bankBal = cursor.getString(1);
                logMessage("db", bankBal);
            } while (cursor.moveToNext());
            logMessage("db", "if " + bankBal);
        } else {
            logMessage("db", "else " + bankBal);
        }

        logMessage("wallet bal ", bankBal);


        int oldBal = Integer.parseInt(bankBal);
        int newBal = oldBal - amount;

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("balance", newBal);
        String whereClause = "id=?";
        String whereArgs[] = {id};
        int cnt = db.update(WALLET_TABLE, contentValues, whereClause, whereArgs);

        cursor.close();

        if (cnt > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addToWallet(int amount) {

        SQLiteDatabase database = getReadableDatabase();
        String columns[] = {"id", "balance"};
        Cursor cursor = database.query(WALLET_TABLE, columns, null, null, null, null, null);

        String walletBal = null, id = null;

        int count = cursor.getCount();

        if (count > 0) {
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                walletBal = cursor.getString(1);
                logMessage("db", walletBal);
            } while (cursor.moveToNext());
            logMessage("db", "if " + walletBal);
        } else {
            logMessage("db", "else " + walletBal);
        }

        logMessage("banck bal ", walletBal);


        int oldBal = Integer.parseInt(walletBal);
        int newBal = oldBal + amount;

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("balance", newBal);
        String whereClause = "id=?";
        String whereArgs[] = {id};
        int cnt = db.update(WALLET_TABLE, contentValues, whereClause, whereArgs);

        cursor.close();

        if (cnt > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addToBank(int amount) {

        SQLiteDatabase database = getReadableDatabase();
        String columns[] = {"id", "balance"};
        Cursor cursor = database.query(BANK_TABLE, columns, null, null, null, null, null);

        String bankBal = null, id = null;

        int count = cursor.getCount();

        if (count > 0) {
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                bankBal = cursor.getString(1);
                logMessage("db", bankBal);
            } while (cursor.moveToNext());
            logMessage("db", "if " + bankBal);
        } else {
            logMessage("db", "else " + bankBal);
        }

        logMessage("banck bal ", bankBal);

        int newBal = 0;
        if (bankBal == null) {
            newBal = amount;
        } else {

            int oldBal = Integer.parseInt(bankBal);
            newBal = oldBal + amount;
        }


        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("balance", newBal);
        String whereClause = "id=?";
        String whereArgs[] = {id};
        int cnt = db.update(BANK_TABLE, contentValues, whereClause, whereArgs);

        cursor.close();

        if (cnt > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> getItemsList() {

        SQLiteDatabase database = getReadableDatabase();
        String columns[] = {"itemId", "itemName"};

        Cursor cursor = database.query(ITEM_TABLE, columns, null, null, null, null, null);

        ArrayList<String> itemsList = null;

        String itemName = null;

        int count = cursor.getCount();

        if (count > 0) {
            itemsList = new ArrayList<>(count);
            cursor.moveToFirst();
            do {
                itemName = cursor.getString(1);
                itemsList.add(itemName);
                logMessage("db", itemName);
            } while (cursor.moveToNext());
            logMessage("db", "if " + itemName);
        } else {
            logMessage("db", "else " + itemName);
        }


        cursor.close();

        return itemsList;

    }

    public boolean savePurchase(BeanPurchase purchase) {

        SQLiteDatabase database = getWritableDatabase();

        long count = 0;

        String item = purchase.getItem();
        int amount = purchase.getAmount();
        String desc = purchase.getDesc();
        String date = purchase.getDate();
        String subItem = purchase.getSubItem();
        String paymentBy = purchase.getPaymentBy();

        Log.e("123", "   Item " + item + "     subItem : " + subItem);

        Log.e("item", "" + item);

        boolean uic = updateItemCounter(item, amount);
        Log.e("savePurchase", "updateItemCounter  " + uic);

        ContentValues values = new ContentValues();
        //columns   id,item,amount,desc,date,paymentBy
        values.put("item", item);
        values.put("amount", amount);
        values.put("desc", desc);
        values.put("date", date);
        values.put("paymentBy", paymentBy);

        count = database.insert(TRANSACTIONS_TABLE, null, values);

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean saveTransaction(BeanPurchase purchase) {

        SQLiteDatabase database = getWritableDatabase();

        long count = 0;

        String item = purchase.getItem();
        int amount = purchase.getAmount();
        String desc = purchase.getDesc();
        String date = purchase.getDate();
        String subItem = purchase.getSubItem();
        String paymentBy = purchase.getPaymentBy();

        logMessage("loanTr", " item " + item + " amount " + amount + " desc " + desc + " date " + date + " Pay By " + paymentBy);

        ContentValues values = new ContentValues();
        //columns   id,item,amount,desc,date,paymentBy
        values.put("item", item);
        values.put("amount", amount);
        values.put("desc", desc);
        values.put("date", date);
        values.put("paymentBy", paymentBy);

        count = database.insert(TRANSACTIONS_TABLE, null, values);

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean saveNewItem(String item) {

        database = getWritableDatabase();

        String whereClause = "itemName=?";
        String whereArgs[] = {"Other"};
        database.delete(ITEM_TABLE, whereClause, whereArgs);

        ContentValues values = new ContentValues();
        //columns   itemId,itemName
        values.put("itemName", item);

        long count = database.insert(ITEM_TABLE, null, values);

        Log.e("tag", "loacn count " + count);

        ContentValues values1 = new ContentValues();
        //columns   itemId,itemName
        values1.put("itemName", "Other");

        long count1 = database.insert(ITEM_TABLE, null, values1);

        if (count > 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean saveNewTransaction(String newItem, int amount) {

        //columns id,type,moneySpend,priority,date
        //columns   id,type,noOfTrans,moneySpend,priority

        ContentValues values = new ContentValues();
        //columns   itemId,itemName
        values.put("type", newItem);
        values.put("noOfTrans", 1);
        values.put("moneySpend", amount);
        values.put("priority", "LOW");

        long count = database.insert(TRANS_STATS_TABLE, null, values);

        Log.e("tag", "loacn count " + count);

        if (count > 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateLoginDetails(String name, String password, String nickname) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        String whereClause = "nickname=?";
        String whereArgs[] = {nickname};
        int cnt = db.update(LOGIN_TABLE, contentValues, whereClause, whereArgs);

        if (cnt > 0) {
            return true;
        } else {
            return false;
        }


    }

    private boolean updateItemCounter(String item, int amount) {

        //columns   id,type,noOfTrans

        SQLiteDatabase database = getReadableDatabase();
        String columns[] = {"id", "type", "noOfTrans", "moneySpend", "priority"};
        Cursor cursor = database.query(TRANS_STATS_TABLE, columns, "type=?", new String[]{item}, null, null, null);

        String type = null, id = null;
        int noOfTrans = 0, amt = 0;

        int count = cursor.getCount();

        if (count > 0) {
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                type = cursor.getString(1);
                noOfTrans = cursor.getInt(2);
                amt = cursor.getInt(3);
                logMessage("db", id + "  " + type + " " + noOfTrans + " " + amt);
            } while (cursor.moveToNext());
            logMessage("db", "if " + noOfTrans);
        } else {
            logMessage("db", "else " + noOfTrans);
        }

        int newAmt = amt + amount;
        int newCouter = noOfTrans + 1;

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("noOfTrans", newCouter);
        contentValues.put("moneySpend", newAmt);
        String whereClause = "type=?";
        String whereArgs[] = {item};
        int cnt = db.update(TRANS_STATS_TABLE, contentValues, whereClause, whereArgs);

        cursor.close();

        if (cnt > 0) {
            return true;
        } else {
            return false;
        }

    }

    public ArrayList<BeanItemStates> getItemStatesList() {

        SQLiteDatabase database = getReadableDatabase();

        String columns[] = {"id", "type", "noOfTrans", "moneySpend", "priority"};

        Cursor cursor = database.query(TRANS_STATS_TABLE, columns, null, null, null, null, null);

        ArrayList<BeanItemStates> itemsList = null;

        String id = null, type = null, noOfTrans = null, moneySpend = null, priority = null;

        int count = cursor.getCount();

        if (count > 0) {
            itemsList = new ArrayList<>(count);
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                type = cursor.getString(1);
                noOfTrans = cursor.getString(2);
                moneySpend = cursor.getString(3);
                priority = cursor.getString(4);
                BeanItemStates itemStates = new BeanItemStates(id, type, noOfTrans, moneySpend, priority);
                itemsList.add(itemStates);
                logMessage("db", id + " " + type + " " + noOfTrans);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return itemsList;


    }

    public void printTransactions() {

        SQLiteDatabase database = getReadableDatabase();
        String columns[] = {"id", "item", "amount", "desc", "date"};
        Cursor cursor = database.query(TRANSACTIONS_TABLE, columns, null, null, null, null, null);

        String id, item, amount, desc, date;

        int count = cursor.getCount();

        if (count > 0) {
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                item = cursor.getString(1);
                amount = cursor.getString(2);
                desc = cursor.getString(3);
                date = cursor.getString(4);
                logMessage("printTransactions", " " + id + " " + item + " " + amount + " " + desc + " " + date);
            } while (cursor.moveToNext());
        } else {
            logMessage("printTransactions", "  No transaction found...........!!!!!! ");
        }

        cursor.close();

    }

    public void printTransType() {

        SQLiteDatabase database = getReadableDatabase();
        String columns[] = {"id", "type", "noOfTrans"};
        Cursor cursor = database.query(TRANS_STATS_TABLE, columns, null, null, null, null, null);

        String id, item, noOfTrans, desc, date;

        int count = cursor.getCount();

        if (count > 0) {
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                item = cursor.getString(1);
                noOfTrans = cursor.getString(2);
                logMessage("printTransType", " " + id + " " + item + " " + noOfTrans);
            } while (cursor.moveToNext());
        } else {
            logMessage("printTransType", "Npo transaction found...........!!!!!! ");
        }

        cursor.close();

    }

    public void printMonthGraphDetails() {


        //columns id,type,moneySpend,priority,date

        SQLiteDatabase database = getReadableDatabase();
        String columns[] = {"id", "type", "moneySpend", "priority", "date"};
        Cursor cursor = database.query(TRANS_TYPE_BY_DATE, columns, null, null, null, null, null);

        String id, type, moneySpend, priority, date;

        int count = cursor.getCount();

        if (count > 0) {
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                type = cursor.getString(1);
                moneySpend = cursor.getString(2);
                priority = cursor.getString(3);
                date = cursor.getString(4);
                logMessage("printTransactions", " Id " + id + " Ty " + type + " Mo " + moneySpend + " P " + priority + " D " + date);
            } while (cursor.moveToNext());
        } else {
            logMessage("printTransactions", "  No transaction found...........!!!!!! ");
        }

        cursor.close();

    }

    public ArrayList<BeanTransactions> getTransactionsList() {

        SQLiteDatabase database = getReadableDatabase();

        String columns[] = {"id", "item", "amount", "desc", "date", "paymentBy"};

        Cursor cursor = database.query(TRANSACTIONS_TABLE, columns, null, null, null, null, null);

        ArrayList<BeanTransactions> itemsList = null;

        String id = null, item = null, amount = null, desc = null, date = null, paymentBy = null;

        int count = cursor.getCount();

        if (count > 0) {
            itemsList = new ArrayList<>(count);
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                item = cursor.getString(1);
                amount = cursor.getString(2);
                desc = cursor.getString(3);
                date = cursor.getString(4);
                paymentBy = cursor.getString(5);
                BeanTransactions transactions = new BeanTransactions(item, amount, desc, date, paymentBy);
                itemsList.add(transactions);
                logMessage("db", id + " item " + item + " amount " + amount + " desc " + desc + " date " + date);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return itemsList;

    }

    public ArrayList<BeanTransactions> getTransactionsListByType(String type) {

        SQLiteDatabase database = getReadableDatabase();

        String columns[] = {"id", "item", "amount", "desc", "date", "paymentBy"};

        Cursor cursor = database.query(TRANSACTIONS_TABLE, columns, "paymentBy=?", new String[]{type}, null, null, null);

        ArrayList<BeanTransactions> itemsList = null;

        String id = null, item = null, amount = null, desc = null, date = null, paymentBy = null;

        int count = cursor.getCount();

        if (count > 0) {
            itemsList = new ArrayList<>(count);
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                item = cursor.getString(1);
                amount = cursor.getString(2);
                desc = cursor.getString(3);
                date = cursor.getString(4);
                paymentBy = cursor.getString(5);
                BeanTransactions transactions = new BeanTransactions(item, amount, desc, date, paymentBy);
                itemsList.add(transactions);
                logMessage("db", id + " item " + item + " amount " + amount + " desc " + desc + " date " + date);
            } while (cursor.moveToNext());

        }

        cursor.close();

        return itemsList;

    }

    public ArrayList<BeanTransactionsHelper> getSingleTransactionsList() {

        SQLiteDatabase database = getReadableDatabase();

        String columns[] = {"id", "item", "amount", "desc", "date", "paymentBy"};

        Cursor cursor = database.query(TRANSACTIONS_TABLE, columns, null, null, null, null, null);

        ArrayList<BeanTransactionsHelper> itemsList = null;

        String id = null, item = null, amount = null, desc = null, date = null, paymentBy = null;

        int count = cursor.getCount();
        String state = "unsel";

        if (count > 0) {
            itemsList = new ArrayList<>(count);
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                item = cursor.getString(1);
                amount = cursor.getString(2);
                desc = cursor.getString(3);
                date = cursor.getString(4);
                paymentBy = cursor.getString(5);
                BeanTransactionsHelper transactions = new BeanTransactionsHelper(item, amount, desc, date, state, paymentBy);
                itemsList.add(transactions);
                logMessage("db", id + " item " + item + " amount " + amount + " desc " + desc + " date " + date);
            } while (cursor.moveToNext());

        }

        cursor.close();


        return itemsList;

    }

    public ArrayList<BeanLoan> getLoanList(String type) {

        SQLiteDatabase database = getReadableDatabase();

        //columns   id,tofrom,amount,date,desc,type

        String columns[] = {"id", "tofrom", "amount", "date", "returnDate", "desc", "type"};

        Cursor cursor = database.query(LOAN_TABLE, columns, "type=?", new String[]{type}, null, null, null);

        ArrayList<BeanLoan> loanArrayList = null;

        String id = null, from = null, amount = null, date = null, returnDate = null, desc = null, tp = null;

        int count = cursor.getCount();

        if (count > 0) {
            loanArrayList = new ArrayList<>(count);
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                from = cursor.getString(1);
                amount = cursor.getString(2);
                date = cursor.getString(3);
                returnDate = cursor.getString(4);
                desc = cursor.getString(5);
                tp = cursor.getString(6);
                Log.e("type", "DB type : " + tp);
                BeanLoan loan = new BeanLoan(id, from, amount, date, returnDate, desc, tp);
                loanArrayList.add(loan);
            } while (cursor.moveToNext());

        }

        cursor.close();

        return loanArrayList;


    }

    public ArrayList<BeanLoanHelper> getSingleLoanList() {

        SQLiteDatabase database = getReadableDatabase();

        //columns   id,tofrom,amount,date,desc,type

        String columns[] = {"id", "tofrom", "amount", "date", "returnDate", "desc", "type"};

        Cursor cursor = database.query(LOAN_TABLE, columns, null, null, null, null, null);

        ArrayList<BeanLoanHelper> loanHelperList = null;

        String id = null, from = null, amount = null, date = null, returnDate = null, desc = null, tp = null;

        int count = cursor.getCount();
        String state = "unselected";
        if (count > 0) {
            loanHelperList = new ArrayList<>(count);
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                from = cursor.getString(1);
                amount = cursor.getString(2);
                date = cursor.getString(3);
                returnDate = cursor.getString(4);
                desc = cursor.getString(5);
                tp = cursor.getString(6);

                BeanLoanHelper loan = new BeanLoanHelper(id, from, amount, date, returnDate, desc, tp, state);

                loanHelperList.add(loan);
            } while (cursor.moveToNext());

        }


        cursor.close();

        return loanHelperList;

    }

    public ArrayList<BeanStatesByDate> getStatsByDate() {

        //columns id,type,moneySpend,priority,date

        SQLiteDatabase database = getReadableDatabase();

        String columns[] = {"id", "type", "moneySpend", "priority", "date"};

        Cursor cursor = database.query(TRANS_TYPE_BY_DATE, columns, null, null, null, null, null);

        ArrayList<BeanStatesByDate> itemsList = null;

        String id = null, type = null, moneySpend = null, priority = null, date = null, paymentBy = null;

        int count = cursor.getCount();

        if (count > 0) {
            itemsList = new ArrayList<>(count);
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                type = cursor.getString(1);
                moneySpend = cursor.getString(2);
                priority = cursor.getString(3);
                date = cursor.getString(4);
                BeanStatesByDate transactions = new BeanStatesByDate(id, type, moneySpend, priority, date);
                itemsList.add(transactions);
                logMessage("statsDate ", id + " type " + type + " moneySpend " + moneySpend + " priority " + priority + " date " + date);
            } while (cursor.moveToNext());

        }

        cursor.close();

        return itemsList;

    }

    public boolean saveLoanDetails(BeanLoan loan) {

        SQLiteDatabase database = getWritableDatabase();

        long count = 0;

        String id = loan.getId();
        String from = loan.getFrom();
        String amount = loan.getAmount();
        String date = loan.getDate();
        String returnDate = loan.getReturnDate();
        String desc = loan.getDesc();
        String type = loan.getType();

        int amt = Integer.parseInt(amount);

        //columns   id,from,amount,date,desc,type
        ContentValues values = new ContentValues();
        //columns   id,item,amount,desc,date
        values.put("tofrom", from);
        values.put("amount", amt);
        values.put("date", date);
        values.put("returnDate", returnDate);
        values.put("desc", desc);
        values.put("type", type);

        count = database.insert(LOAN_TABLE, null, values);

        Log.e("tag", "loacn count " + count);

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean saveStatsByDate(BeanStatesByDate statesByDate) {

        SQLiteDatabase database = getWritableDatabase();

        long count = 0;

        String id = statesByDate.getId();
        String type = statesByDate.getType();
        String moneySpend = statesByDate.getMoneySpend();
        String priority = statesByDate.getPriority();
        String date = statesByDate.getDate();

        int money = Integer.parseInt(moneySpend);

        //columns id,type,moneySpend,priority,date
        ContentValues values = new ContentValues();
        //columns   id,item,amount,desc,date
        values.put("type", type);
        values.put("moneySpend", money);
        values.put("priority", priority);
        values.put("date", date);

        count = database.insert(TRANS_TYPE_BY_DATE, null, values);

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteTransactionBatch(String itemSel) {

        //columns   id,item,amount,desc,date

        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "item=?";
        String whereArgs[] = {itemSel};
        int cnt = db.delete(TRANSACTIONS_TABLE, whereClause, whereArgs);
        if (cnt > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean clearSelectedTransactions(ArrayList<BeanTransactionsHelper> transactionHelperList) {

        //columns   id,item,amount,desc,date

        SQLiteDatabase db = getWritableDatabase();
        int cnt = 0;
        for (int i = 0; i < transactionHelperList.size(); i++) {

            BeanTransactionsHelper transaction = transactionHelperList.get(i);
            String state = transaction.getState();
            String item = transaction.getItem();
            String amount = transaction.getAmount();
            String date = transaction.getDate();
            String desc = transaction.getDesc();
            if (state.equalsIgnoreCase("selected")) {
                String whereClause = "item=? and amount=? and desc=? and date=?";
                String whereArgs[] = {item, amount, desc, date};
                cnt = db.delete(TRANSACTIONS_TABLE, whereClause, whereArgs);
            }
        }


        if (cnt > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean clearSelectedLoans(ArrayList<BeanLoanHelper> loanHelperList) {

        //columns   id,tofrom,amount,date,desc,type

        SQLiteDatabase db = getWritableDatabase();
        int cnt = 0;
        String tag = "loan";
        for (int i = 0; i < loanHelperList.size(); i++) {

            BeanLoanHelper transaction = loanHelperList.get(i);
            String state = transaction.getState();
            String from = transaction.getFrom();
            String amount = transaction.getAmount();
            String date = transaction.getDate();
            String desc = transaction.getDesc();
            String type = transaction.getType();

            Log.e(tag, "------------------------ " + i + " -------------------------------");
            Log.e(tag, "--state " + state + " --");
            Log.e(tag, "--from " + from + " --");
            Log.e(tag, "--amount " + amount + " --");
            Log.e(tag, "--date " + date + " --");
            Log.e(tag, "--desc " + desc + " --");
            Log.e(tag, "--type " + type + " --");

            if (state.equalsIgnoreCase("selected")) {
                String whereClause = "tofrom=? and amount=?";
                String whereArgs[] = {from, amount};
                cnt = db.delete(LOAN_TABLE, whereClause, whereArgs);
            }
        }


        if (cnt > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean saveChngedItemPriorities(ArrayList<BeanItemStates> itemStatesList) {

        int cnt = 0;

        for (int i = 0; i < itemStatesList.size(); i++) {

            BeanItemStates selItem = itemStatesList.get(i);

            Log.e("itemPri", "Item " + selItem.getType() + " Priority " + selItem.getPriority());

            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("type", selItem.getType());
            contentValues.put("priority", selItem.getPriority());
            String whereClause = "type=?";
            String whereArgs[] = {selItem.getType()};

            cnt = db.update(TRANS_STATS_TABLE, contentValues, whereClause, whereArgs);

        }

        //columns   id,type,noOfTrans,moneySpend,priority

        if (cnt > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean saveMonthGraphDetails(String date) {

        //columns id,type,moneySpend,priority,date

        long cnt = 0;
        String id = null, type = null, noOfTrans = null, moneySpend = null, priority = null;

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        ArrayList<BeanItemStates> itemStatesList = getItemStatesList();

        Log.e("saveMonthDetails", "  itemStatesList.size()   " + itemStatesList.size());

        for (int i = 0; i < itemStatesList.size(); i++) {

            BeanItemStates itemStates = itemStatesList.get(i);
            type = itemStates.getType();
            noOfTrans = itemStates.getNoOfTrans();
            moneySpend = itemStates.getMoneySpend();
            priority = itemStates.getPriority();

            contentValues.put("type", type);
            contentValues.put("moneySpend", moneySpend);
            contentValues.put("priority", priority);
            contentValues.put("date", date);

            cnt = db.insert(TRANS_TYPE_BY_DATE, null, contentValues);

            Log.e("saveMonthDetails1", " i : " + i);
            Log.e("saveMonthDetails2", "count records changed : " + cnt);
            Log.e("saveMonthDetails3", "--------------------------------------------------------- ");

        }

        if (cnt > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean initializeTransStatsToZero(ArrayList<BeanItemStates> itemStatesList) {

        //columns   id,type,noOfTrans,moneySpend,priority

        Log.e("initializeToZero", "  itemStatesList.size()   " + itemStatesList.size());

        SQLiteDatabase db = getWritableDatabase();

        int cnt = 0;

        for (int i = 0; i < itemStatesList.size(); i++) {

            String item = itemStatesList.get(i).getType();
            ContentValues contentValues = new ContentValues();
            contentValues.put("noOfTrans", 0);
            contentValues.put("moneySpend", 0);
            String whereClause = "type=?";
            String whereArgs[] = {item};
            cnt = db.update(TRANS_STATS_TABLE, contentValues, whereClause, whereArgs);

            Log.e("initializeToZero1", " i : " + i);
            Log.e("initializeToZero2", "count records changed : " + cnt);
            Log.e("initializeToZero3", "--------------------------------------------------------- ");

        }


        if (cnt > 0) {
            return true;
        } else {
            return false;
        }

    }

//    table : tbl_split;
//    columns  id,title,amount,date,returnDate,comment,participants,owes

    public boolean saveSplitRecord(BeanSplit split) {

        long count = 0;

        SQLiteDatabase database = getWritableDatabase();

        String title = split.getTitle();
        String amount = split.getAmount();
        String date = split.getDate();
        String returnDate = split.getReturnDate();
        String comment = split.getComment();
        String owes = split.getOwesYou();
        float ow = Float.parseFloat(owes);
        int owesMe = (int) ow;

        ArrayList<String> list = split.getParticipantsList();

        for (int i = 0; i < list.size(); i++) {

            String participant = list.get(i);

            Log.e("db123", " title " + title + " amount " + amount + " date " + date + " returnDate " + returnDate + " comment " + comment + " participant " + participant + " owesYou " + owesMe);

            ContentValues values = new ContentValues();
            //columns   itemId,itemName
            values.put("title", title);
            values.put("amount", amount);
            values.put("date", date);
            values.put("returnDate", returnDate);
            values.put("comment", comment);
            values.put("participants", participant);
            values.put("owes", owesMe);

            count = database.insert(SPLIT_TABLE, null, values);

            Log.e("tag", "loacn count " + count);
        }


        if (count > 1) {
            return true;
        } else {
            return false;
        }

    }

    public ArrayList<BeanSplitHelper> getSplitBillList() {

//    columns  id,title,amount,date,returnDate,comment,participants,owes

        SQLiteDatabase database = getReadableDatabase();

        String columns[] = {"id", "title", "amount", "date", "returnDate", "comment", "participants", "owes"};

        Cursor cursor = database.query(SPLIT_TABLE, columns, null, null, null, null, null);

        ArrayList<BeanSplitHelper> itemsList = null;

        String id = null, title = null, amount = null, date = null, comment = null, participant = null, owes = null, returnDate = null;

        int count = cursor.getCount();

        if (count > 0) {
            itemsList = new ArrayList<>(count);
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                title = cursor.getString(1);
                amount = cursor.getString(2);
                date = cursor.getString(3);
                returnDate = cursor.getString(4);
                comment = cursor.getString(5);
                participant = cursor.getString(6);
                owes = cursor.getString(7);

                Log.e("db144", " title " + title + " amount " + amount + " date " + date + " returnDate " + returnDate + " comment " + comment + " participant " + participant + " owesYou " + owes);

                BeanSplitHelper helper = new BeanSplitHelper(title, amount, date, returnDate, comment, owes, participant);
                itemsList.add(helper);
                logMessage("splitSingle ", id + " title " + title + " amount " + amount + " date " + date + " participant " + participant + " owes " + owes);
            } while (cursor.moveToNext());

        }


        cursor.close();

        return itemsList;

    }

    public BeanThought getTodaysThought(int id) {

        String THOUGHT_TABLE = "tbl_thoughts";
        String idd = "" + id;
        SQLiteDatabase database = getReadableDatabase();
        String columns[] = {"id", "auther", "thought"};
        Cursor cursor = database.query(THOUGHT_TABLE, columns, "id=?", new String[]{idd}, null, null, null);

        String id1 = null, auther = null, thought1 = null;

        int count = cursor.getCount();
        BeanThought thought = null;

        if (count > 0) {
            cursor.moveToFirst();
            do {
                id1 = cursor.getString(0);
                auther = cursor.getString(1);
                thought1 = cursor.getString(2);

                thought = new BeanThought(auther, thought1);
            } while (cursor.moveToNext());


        }

        cursor.close();

        return thought;
    }

    public boolean updateSplitBill(String title, String amount, String date, String participant, int paidYou1) {


        Log.e("updateSplit_DB", " title " + title + " amount " + amount + " date " + date + " paidYou1 " + paidYou1 + " participant " + participant);

        SQLiteDatabase database = getReadableDatabase();

        String columns[] = {"id", "owes"};

        String selection = "title=? AND amount=? AND date=? AND participants=?";
        Cursor cursor = database.query(SPLIT_TABLE, columns, selection, new String[]{title, amount, date, participant}, null, null, null, null);


        String owes = null, id = null;

        int count = cursor.getCount();

        if (count > 0) {
            cursor.moveToFirst();
            do {
                id = cursor.getString(0);
                owes = cursor.getString(1);
                logMessage("db", "Owes You : " + owes);
            } while (cursor.moveToNext());
            logMessage("db", "if " + owes);
        } else {
            logMessage("db", "else " + owes);
        }

        logMessage("banck bal ", owes);


        cursor.close();

        int oldBal = Integer.parseInt(owes);
        int newBal = oldBal - paidYou1;

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("owes", newBal);
        String whereClause = "title=? AND amount=? AND date=? AND participants=?";
        String whereArgs[] = {title, amount, date, participant};
        int cnt = db.update(SPLIT_TABLE, contentValues, whereClause, whereArgs);


        if (cnt > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean clearSelectedSplitBills(ArrayList<BeanSplitFinalHelper> listHelper) {

        SQLiteDatabase db = getWritableDatabase();
        int cnt = 0;
        for (int i = 0; i < listHelper.size(); i++) {

            BeanSplitFinalHelper bill = listHelper.get(i);
            String title = bill.getTitle();
            String date = bill.getDate();
            String state = bill.getState();
            Log.e("jokerer", " *-*- *-* -* -* -* -* - *- *- - - - -  " + state + "  D  " + date);
            if (state.equalsIgnoreCase("selected")) {
                String whereClause = "title=? AND date=?";
                String whereArgs[] = {title, date};
                cnt = db.delete(SPLIT_TABLE, whereClause, whereArgs);
            }
        }


        if (cnt > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean clearMonthDetailTransactions(ArrayList<BeanStatesByDateFinalHelper> listHelper) {
        SQLiteDatabase db = getWritableDatabase();
        int cnt = 0;
        for (int i = 0; i < listHelper.size(); i++) {

            BeanStatesByDateFinalHelper bill = listHelper.get(i);
            String date = bill.getDate();
            String state = bill.getState();
            if (state.equalsIgnoreCase("selected")) {
                String whereClause = "date=?";
                String whereArgs[] = {date};
                cnt = db.delete(TRANS_TYPE_BY_DATE, whereClause, whereArgs);
            }
        }


        if (cnt > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateLoanBill(String from, String date, String amount, String type, String desc, int paidYou1) {

        int amt = Integer.parseInt(amount);
        int newBal = amt - paidYou1;

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", newBal);
        String whereClause = "tofrom=? AND amount=? AND date=? AND type=?";
        String whereArgs[] = {from, amount, date, type};
        int cnt = db.update(LOAN_TABLE, contentValues, whereClause, whereArgs);

        if (cnt > 0) {
            return true;
        } else {
            return false;
        }

    }
}
