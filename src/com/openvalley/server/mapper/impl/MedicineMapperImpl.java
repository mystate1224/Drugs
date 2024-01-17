package com.openvalley.server.mapper.impl;

import com.openvalley.server.entity.Medicine;
import com.openvalley.server.helper.JDBCHelper;
import com.openvalley.server.mapper.MedicineMapper;
import com.openvalley.server.query.MedicineQuery;

import java.util.List;

public class MedicineMapperImpl implements MedicineMapper {
    public static final JDBCHelper JDBC = new JDBCHelper();
    public List<Medicine> search(MedicineQuery query){
        String sql=getSQLFromMedicineQuery(query);
        return JDBC.select(sql,null,Medicine.class);
    }

    @Override
    public Boolean delete(Integer id) {
        String sql="delete from medicine where id=?";
        String[] values=new String[]{String.valueOf(id)};
        return JDBC.delete(sql,values)>0;
    }

    @Override
    public Boolean update(Medicine medicine) {
        String sql="update medicine set medicineNo=?,name=?,factoryAddress=?,description=?,price=?,expire=?,unit=?,number=?,categoryId=? where id=?";
        String[] values={
                medicine.getMedicineNo(),
                medicine.getName(),
                medicine.getFactoryAddress(),
                medicine.getDescription(),
                String.valueOf(medicine.getPrice()),
                String.valueOf(medicine.getPrice()),
                String.valueOf(medicine.getExpire()),
                medicine.getUnit(),
                String.valueOf(medicine.getNumber()),
                String.valueOf(medicine.getCategoryId()),
                String.valueOf(medicine.getId())
        };
        return JDBC.update(sql,values)>0;
    }

    private String getSQLFromMedicineQuery(MedicineQuery query) {
    String sql=new String("select *from medicine where deleted =0");
    if(query==null)return sql;
        if (query.getName() != null) {
            sql+=" AND `name` LIKE \"%" + query.getName() + "%\"";
        }

        if (query.getMinPrice() != null){
            sql+=" AND price >= " + query.getMinPrice();
        }

        if (query.getMaxPrice() != null){
            sql+=" AND price <= " + query.getMaxPrice();
        }

        if (query.getExpire() != null){
            sql+=" AND expire <= \"" + query.getExpire() + "\"";
        }

        if (query.getCategoryId() != null){
            sql+=" AND categoryId = " + query.getCategoryId();
        }
        return sql;
    }

    @Override
    public Boolean insert(Medicine medicine) {

        String sql = "INSERT INTO medicine(medicineNo,name,factoryAddress,description,price,expire,unit,number,categoryId,deleted) values=(?,?,?,?,?,?,?,?,?,?)";
        String[] values = new String[]{
                medicine.getMedicineNo(),
                medicine.getName(),
                medicine.getFactoryAddress(),
                medicine.getDescription(),
                String.valueOf(medicine.getPrice()),
                String.valueOf(medicine.getExpire()),
                medicine.getUnit(),
                String.valueOf(medicine.getNumber()),
                String.valueOf(medicine.getCategoryId()),
                String.valueOf(medicine.getDeleted())
        };
        return JDBC.insert(sql,values)>0;
    }

    @Override
    public Medicine selectById(Integer id) {
        String sql="select * from medicine where id=?";
        String[] values={String.valueOf(id)};
        List<Medicine> medicineList=JDBC.select(sql,values,Medicine.class);
        return medicineList.isEmpty()?null:medicineList.get(0);
    }

    @Override
    public List<Medicine> selectAllMedicine() {
        String sql="select *from medicine";
        return JDBC.select(sql,null,Medicine.class);
    }
}
