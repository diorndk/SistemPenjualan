/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.org.sistempenjualan.dao.impl;

import com.org.sistempenjualan.DbConnect;
import com.org.sistempenjualan.dao.BarangDao;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author DIOR
 */
public class BarangDaoImpl implements BarangDao{
    Connection con = DbConnect.ConnectDb();
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    @Override
    public ResultSet setBarangTable() {
        ResultSet res = null;
        try{
            String sql = "SELECT a.id_barang, "
                        + "a.nama_barang as 'Nama Barang', "
                        + "a.jumlah Jumlah, "
                        + "a.harga Harga, "
                        + "b.nama_supplier Supplier "
                        + "FROM mst_barang a, mst_supplier b "
                        + "WHERE a.kode_supplier = b.kode_supplier ";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
        } catch (SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null,"Error set Data Table ("+e.toString()+")");
        }
        return res;
    }

    @Override
    public int getLastStok(String namaBarang) {
        int result = 0;
        try{
            String sql = "SELECT a.jumlah "
                        + "FROM mst_barang a "
                        + "WHERE a.nama_barang = ? ";
            pst = con.prepareStatement(sql);
            pst.setString(1, namaBarang);
            rs = pst.executeQuery();
            if(rs.next()){
                result = rs.getInt(1);
            }
        } catch (SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null,"Error get Stok ("+e.toString()+")");
        }
        return result;
    }
    
    @Override
    public Vector getListBarang() {
        Vector vector = new Vector();
        try {
            String sql = "SELECT CONCAT(a.kode_barang, ' - ', a.nama_barang)  "
                        + "FROM mst_barang a "
                        + "ORDER BY a.kode_barang ASC ";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            vector.add("-- Pilih Barang --");
            while (rs.next()) {
                vector.add(rs.getString(1));
            }
        } catch (SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null,"Error Get Data Barang ("+e.toString()+")");
        }
        return vector;
    }

    @Override
    public boolean updateStokBarang(String kodeBarang, int jumlah) {
        boolean result = false;
        try{
            String sql = "UPDATE mst_barang a "
                        + "SET a.jumlah = a.jumlah - ? "
                        + "WHERE a.kode_barang = ? ";
            pst = con.prepareStatement(sql);
            pst.setInt(1, jumlah);
            pst.setString(2, kodeBarang);
            pst.execute();
            result = true;
        } catch (SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null,"Error Update Stok Barang ("+e.toString()+")");
        }
        
        return result;
    }

    @Override
    public boolean returnStokBarang(String kodeBarang, int jumlah) {
        boolean result = false;
        try{
            String sql = "UPDATE mst_barang a "
                        + "SET a.jumlah = a.jumlah + ? "
                        + "WHERE a.kode_barang = ? ";
            pst = con.prepareStatement(sql);
            pst.setInt(1, jumlah);
            pst.setString(2, kodeBarang);
            pst.execute();
            result = true;
        } catch (SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null,"Error Update Stok Barang ("+e.toString()+")");
        }
        
        return result;
    }
    
}