package com.br.kjtrufas.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.br.kjtrufas.entidades.Token;

public class TokenDAO {

    private static ContentValues preencherContentValues(Token token)
    {
        ContentValues values = new ContentValues();

        values.put("ID",token.getId());
        values.put("ACCESS_TOKEN",token.getAccess_token());
        values.put("INTANCE_URL",token.getInstance_url());
        values.put("TOKEN_TYPE",token.getToken_type());
        values.put("ISSUED_AT",token.getIssued_at());
        values.put("SIGNATURE",token.getSignature());

        return values;
    }

    public static void upsert(Token token, SQLiteDatabase conn)
    {
        Log.i("SQL", String.valueOf(conn));

        if(!hasVendedor(token,conn))
            conn.insertOrThrow("TOKEN_SF", null, preencherContentValues(token));
        else
            conn.update("TOKEN_SF",preencherContentValues(token),null,null);
    }

    public static boolean hasVendedor(Token token, SQLiteDatabase conn)
    {
        Cursor cursor = conn.query("TOKEN_SF",null,null, null,null,null,null);

        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public static void delete(SQLiteDatabase conn)
    {
        conn.delete("TOKEN_SF",null, null);
    }

    public static Token getToken(SQLiteDatabase conn) {

        Token retorno = null;

        Cursor cursor = conn.query("TOKEN_SF", null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            retorno = new Token();

            retorno.setAccess_token(cursor.getString(cursor.getColumnIndex("ACCESS_TOKEN")));
            retorno.setId(cursor.getString(cursor.getColumnIndex("ID")));
            retorno.setInstance_url(cursor.getString(cursor.getColumnIndex("INTANCE_URL")));
            retorno.setIssued_at(cursor.getString(cursor.getColumnIndex("ISSUED_AT")));
            retorno.setSignature(cursor.getString(cursor.getColumnIndex("SIGNATURE")));
            retorno.setToken_type(cursor.getString(cursor.getColumnIndex("TOKEN_TYPE")));
        }

        return retorno;
    }
}
