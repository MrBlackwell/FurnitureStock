import java.sql.*;
import java.util.*;


public class Access {
    private static Connection connection = null;

    static boolean connectDB(String path){
        try{
            String s1="jdbc:ucanaccess://";
            String s4=path+"stock.accdb";
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            connection = DriverManager.getConnection(s1+s4);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public ArrayList<stockType> getStock(int id) {
        try{
            ArrayList<stockType> types = new ArrayList<>();
            ArrayList<stockType> types1 = new ArrayList<>();
            Statement statement = connection.createStatement();
            String request;
            if (id==-1) {
                request = "SELECT SUM(Delivery.count), Materials.name_material FROM Delivery, Materials WHERE " +
                    "Delivery.id_material=Materials.id GROUP BY Materials.name_material";
            } else {
                request = "SELECT SUM(Delivery.count), Materials.name_material FROM Delivery, Materials WHERE Delivery.id_material=Materials.id " +
                        "AND Materials.id_category=" + id + " GROUP BY Materials.name_material ORDER BY Materials.name_material";
            }
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()){
                stockType type = new stockType();
                type.setCount(resultSet.getString(1));
                type.setName_materials(resultSet.getString(2));
                types.add(type);
            }
            if(id==-1) {
                request = "SELECT SUM(Shipment.count), Materials.name_material FROM Shipment, Materials WHERE " +
                    "Shipment.id_material=Materials.id GROUP BY Materials.name_material";
            } else {
                request = "SELECT SUM(Shipment.count), Materials.name_material FROM Shipment, Materials WHERE " +
                        "Shipment.id_material=Materials.id AND Materials.id_category=" + id + " GROUP BY Materials.name_material ORDER BY Materials.name_material";
            }
            resultSet = statement.executeQuery(request);
            while (resultSet.next()){
                stockType type = new stockType();
                type.setCount(resultSet.getString(1));
                type.setName_materials(resultSet.getString(2));
                types1.add(type);
            }
            for (stockType type : types) {
                for (stockType aTypes1 : types1) {
                    if (type.getName_materials().equals(aTypes1.getName_materials())) {
                        int newCount = Integer.parseInt(type.getCount()) - Integer.parseInt(aTypes1.getCount());
                        type.setCount(Integer.toString(newCount));
                    }
                }
            }
            return types;
        } catch (SQLException e){
            return null;
        }
    }

    boolean addMaterial(String name_material, int category){
        try{
            Statement statement = connection.createStatement();
            String request = "INSERT INTO Materials (name_material, id_category) VALUES ('" + name_material + "', "+ category +")";
            statement.executeUpdate(request);
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    ArrayList<Item> getItem(int id_category){
        ArrayList<Item> array = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String request = "SELECT * FROM Materials WHERE id_category="+id_category+" ORDER BY name_material";
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()){
                array.add(new Item(resultSet.getInt(1), resultSet.getString(2)));
            }
            return array;
        } catch (SQLException e){
            return null;
        }
    }

    ArrayList<Item> getCategory(){
        ArrayList<Item> array = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String request = "SELECT `id`, `name_category` FROM `Category` ORDER BY `name_category`";
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()){
                array.add(new Item(resultSet.getInt(1), resultSet.getString(2)));
            }
            return array;
        } catch (SQLException e){
            return null;
        }
    }

    boolean addToStock(String number, String date, int idMaterial, int count){
        try{
            Statement statement = connection.createStatement();
            String request = "INSERT INTO Delivery (invoice, delivery_date, id_material, count) " +
                    "VALUES ('" + number + "', '" + date + "', "+ idMaterial + ", "+ count + ");";
            statement.executeUpdate(request);
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    boolean deleteFromStock(String number, String date, int idMaterial, int count){
        try{
            Statement statement = connection.createStatement();
            String request = "INSERT INTO Shipment (invoice, shipment_date, id_material, count) " +
                    "VALUES ('" + number + "', '" + date + "', "+ idMaterial + ", "+ count + ");";
            statement.executeUpdate(request);
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    ArrayList<invoiceType> getAllInvoice() {
        ArrayList<invoiceType> types = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String request = "SELECT Delivery.invoice, Materials.name_material, Delivery.count, Delivery.delivery_date, " +
                    "Delivery.id, Delivery.id_material FROM Delivery, " +
                    "Materials WHERE Delivery.id_material=Materials.id ORDER BY Delivery.invoice";
            ResultSet resultSet = statement.executeQuery(request);
            int predId = -1;
            while(resultSet.next()){
                if(predId == resultSet.getInt(1)){
                    int i = types.size()-1;
                    String materials = types.get(i).getMaterialInvoice();
                    materials += "\n" + resultSet.getString(2);
                    types.get(i).setMaterialInvoice(materials);
                    String count = types.get(i).getCountMaterials();
                    count += "\n" + resultSet.getString(3);
                    types.get(i).setCountMaterials(count);
                    String id = types.get(i).getId();
                    id += "\n" + resultSet.getString(5);
                    types.get(i).setId(id);
                    id = types.get(i).getId_material();
                    id += "\n" + resultSet.getString(6);
                    types.get(i).setId_material(id);
                    predId = resultSet.getInt(1);
                } else {
                    types.add(new invoiceType(resultSet.getString(5), resultSet.getString(6), resultSet.getString(1), resultSet.getString(4), resultSet.getString(2),
                            resultSet.getString(3), 0));
                    predId = resultSet.getInt(1);
                }
            }
            request = "SELECT Shipment.invoice, Materials.name_material, Shipment.count, Shipment.shipment_date, " +
                    "Shipment.id, Shipment.id_material FROM Shipment, " +
                    "Materials WHERE Shipment.id_material=Materials.id ORDER BY Shipment.invoice";
            resultSet = statement.executeQuery(request);
            predId = -1;
            while(resultSet.next()) {
                if (predId == resultSet.getInt(1)) {
                    int i = types.size() - 1;
                    String materials = types.get(i).getMaterialInvoice();
                    materials += "\n" + resultSet.getString(2);
                    types.get(i).setMaterialInvoice(materials);
                    String count = types.get(i).getCountMaterials();
                    count += "\n" + resultSet.getString(3);
                    types.get(i).setCountMaterials(count);
                    String id = types.get(i).getId();
                    id += "\n" + resultSet.getString(5);
                    types.get(i).setId(id);
                    id = types.get(i).getId_material();
                    id += "\n" + resultSet.getString(6);
                    types.get(i).setId_material(id);
                    predId = resultSet.getInt(1);
                } else {
                    types.add(new invoiceType(resultSet.getString(5), resultSet.getString(6), resultSet.getString(1), resultSet.getString(4), resultSet.getString(2),
                            resultSet.getString(3), 1));
                    predId = resultSet.getInt(1);
                }
            }
        } catch (SQLException ignored){}
        return types;
    }

    boolean updateData(String id, String table, String number, String date, int idMaterial, int count) {
        try{
            Statement statement = connection.createStatement();
            String request = "UPDATE "+table+" SET invoice='"+number+"', "+table.toLowerCase()+"_date='"+date+"', id_material="+idMaterial+
                    ", count="+count+" WHERE id="+id;
            statement.executeUpdate(request);
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    boolean deleteRow(String id, String table){
        try{
            Statement statement = connection.createStatement();
            String request = "DELETE FROM "+table+" WHERE id="+id;
            statement.executeUpdate(request);
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    public boolean editMaterial(int id, String new_name) {
        try{
            Statement statement = connection.createStatement();
            String request = "UPDATE Materials SET name_material='"+new_name+"' WHERE id="+id;
            statement.executeUpdate(request);
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    /**Добавление категории материалов**/
    boolean addCategory(String name){
        try {
            Statement statement = connection.createStatement();
            String reqest = "INSERT INTO Category (name_category) VALUES '"+ name + "'";
            statement.executeUpdate(reqest);
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    /**Редактирование категории**/
    boolean editCategory(int id, String name){
        try{
            Statement statement = connection.createStatement();
            String request = "UPDATE Category SET name_category='"+name+"' WHERE id="+id;
            statement.executeUpdate(request);
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    /**Удаление категории**/
    boolean deleteCategory (int id){
        try{
            Statement statement = connection.createStatement();
            String request = "DELETE FROM Category WHERE id="+id;
            statement.executeUpdate(request);
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    /**Удаление материала**/
    boolean deleteMaterial (int id){
        try{
            Statement statement = connection.createStatement();
            String request = "DELETE FROM Materials WHERE id="+id;
            statement.executeUpdate(request);
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    boolean swapMaterial (int new_id, int material_id){
        try{
            Statement statement = connection.createStatement();
            String request = "UPDATE Materials SET id_category='"+new_id+"' WHERE id="+material_id;
            statement.executeUpdate(request);
            return true;
        } catch (SQLException e){
            return false;
        }
    }
}