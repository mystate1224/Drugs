package com.openvalley.server.mapper;

import com.openvalley.server.entity.Medicine;
import com.openvalley.server.query.MedicineQuery;

import java.util.List;

public interface MedicineMapper {
    List<Medicine> search(MedicineQuery medicineQuery);
    Boolean insert(Medicine medicine);
    Boolean delete(Integer id);
    Boolean update(Medicine medicine);
    Medicine selectById(Integer id);
    List<Medicine> selectAllMedicine();
}
