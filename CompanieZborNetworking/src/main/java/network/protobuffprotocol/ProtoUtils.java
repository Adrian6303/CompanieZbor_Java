package network.protobuffprotocol;

import domain.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

//.\protoc --java_out=C:\Users\adria\IdeaProjects\mpp-proiect-java-Adrian6303\CompanieZborNetworking\src\main\java\network\protobuffprotocol --proto_path=C:\Users\adria\IdeaProjects\mpp-proiect-java-Adrian6303\proto C:\Users\adria\IdeaProjects\mpp-proiect-java-Adrian6303\proto\CZbor.proto

public class ProtoUtils {
    public static CZbor.Request createLoginRequest(Angajat angajat) {
        CZbor.Angajat angajat1 = CZbor.Angajat.newBuilder().setUser(angajat.getUser()).setPassword(angajat.getPassword()).build();
        CZbor.Request request = CZbor.Request.newBuilder().setType(CZbor.Request.RequestType.LOGIN).setAngajat(angajat1).build();
        return request;
    }

    public static CZbor.Request createLogoutRequest(Angajat angajat) {
        CZbor.Angajat angajat1 = CZbor.Angajat.newBuilder().setUser(angajat.getUser()).setPassword(angajat.getPassword()).build();
        CZbor.Request request = CZbor.Request.newBuilder().setType(CZbor.Request.RequestType.LOGOUT).setAngajat(angajat1).build();
        return request;
    }
    public static CZbor.Request createGetZboruriRequest() {
        CZbor.Request request = CZbor.Request.newBuilder().setType(CZbor.Request.RequestType.GET_ZBORURI).build();
        return request;
    }
    public static CZbor.Request createAddBiletRequest(Bilet bilet) {
        CZbor.Angajat angajat1 = CZbor.Angajat.newBuilder().setUser(bilet.getAngajat().getUser()).setPassword(bilet.getAngajat().getPassword()).build();
        CZbor.Zbor zbor1 = CZbor.Zbor.newBuilder().setDestinatia(bilet.getZbor().getDestinatia()).setDataPlecarii(bilet.getZbor().getDataPlecarii().toString()).setNrLocuri(bilet.getZbor().getNrLocuri()).build();
        CZbor.Turist client1 = CZbor.Turist.newBuilder().setNume(bilet.getClient().getNume()).build();
        List<Turist> turists = bilet.getListaTuristi();
        List<CZbor.Turist> turistP = null;
        for(Turist turist : turists){
            CZbor.Turist turist1 = CZbor.Turist.newBuilder().setNume(turist.getNume()).build();
            turistP.add(turist1);
        }
        CZbor.Bilet bilet1 = CZbor.Bilet.newBuilder().setAngajat(angajat1).setZbor(zbor1).setClient(client1).addAllListaTuristi(turistP).setAdresaClient(bilet.getAdresaClient()).setNrLocuri(bilet.getNrLocuri()).build();
        CZbor.Request request = CZbor.Request.newBuilder().setType(CZbor.Request.RequestType.BUY_BILET).setBilet(bilet1).build();
        return request;
    }
    public static CZbor.Request createFindZboruriByDestinatieAndDateRequest(Zbor zbor) {
        CZbor.Zbor zbor1 = CZbor.Zbor.newBuilder().setDestinatia(zbor.getDestinatia()).setDataPlecarii(zbor.getDataPlecarii().toString()).setNrLocuri(zbor.getNrLocuri()).build();
        CZbor.Request request = CZbor.Request.newBuilder().setType(CZbor.Request.RequestType.FILTER_ZBORURI).setZbor(zbor1).build();
        return request;
    }
    public static CZbor.Request createFindOrAddTuristRequest(String nume) {
        CZbor.Request request = CZbor.Request.newBuilder().setType(CZbor.Request.RequestType.FIND_ADD_TURIST).setName(nume).build();
        return request;
    }

    public static CZbor.Request createUpdateZborRequest(Zbor zbor) {
        CZbor.Zbor zbor1 = CZbor.Zbor.newBuilder().setDestinatia(zbor.getDestinatia()).setDataPlecarii(zbor.getDataPlecarii().toString()).setNrLocuri(zbor.getNrLocuri()).build();
        CZbor.Request request = CZbor.Request.newBuilder().setType(CZbor.Request.RequestType.UPDATE_ZBOR).setZbor(zbor1).build();
        return request;
    }
    public static CZbor.Response createOkResponse(){
        CZbor.Response response = CZbor.Response.newBuilder().setType(CZbor.Response.ResponseType.OK).build();
        return response;
    }
    public static CZbor.Response createOkResponse(Angajat angajat){
        CZbor.Angajat angajat1 = CZbor.Angajat.newBuilder().setUser(angajat.getUser()).setPassword(angajat.getPassword()).build();
        CZbor.Response response = CZbor.Response.newBuilder().setType(CZbor.Response.ResponseType.OK).setAngajat(angajat1).build();
        return response;
    }
    

    public static CZbor.Response createErrorResponse(){
        CZbor.Response response = CZbor.Response.newBuilder().setType(CZbor.Response.ResponseType.ERROR).build();
        return response;
    }
    public static CZbor.Response createGetZboruriResponse(List<Zbor> zboruri){
        CZbor.Response.Builder responseBuilder = CZbor.Response.newBuilder().setType(CZbor.Response.ResponseType.GET_ZBORURI);
        for(var zbor : zboruri){
            CZbor.Zbor zborProto = CZbor.Zbor.newBuilder().setDestinatia(zbor.getDestinatia()).setDataPlecarii(zbor.getDataPlecarii().toString()).setNrLocuri(zbor.getNrLocuri()).build();
            responseBuilder.addZboruri(zborProto);
        }
        return responseBuilder.build();
    }


    public static CZbor.Response createFilterZboruriResponse(List<Zbor> zboruri){
        CZbor.Response.Builder responseBuilder = CZbor.Response.newBuilder().setType(CZbor.Response.ResponseType.FILTER_ZBORURI);
        for(var zbor : zboruri){
            CZbor.Zbor zborProto = CZbor.Zbor.newBuilder().setDestinatia(zbor.getDestinatia()).setDataPlecarii(zbor.getDataPlecarii().toString()).setNrLocuri(zbor.getNrLocuri()).build();
            responseBuilder.addZboruri(zborProto);
        }
        return responseBuilder.build();
    }
    public static CZbor.Response createFindAddTuristResponse(Turist turist){
        CZbor.Turist turistProto = CZbor.Turist.newBuilder().setNume(turist.getNume()).build();
        CZbor.Response response = CZbor.Response.newBuilder().setType(CZbor.Response.ResponseType.FIND_ADD_TURIST).setTurist(turistProto).build();
        return response;
    }
    public static CZbor.Response createUpdateZborResponse(){
        CZbor.Response response = CZbor.Response.newBuilder().setType(CZbor.Response.ResponseType.UPDATE_ZBOR).build();
        return response;
    }

    public static Angajat getAngajat(CZbor.Response response){
        var angajat = response.getAngajat();
        return new Angajat(angajat.getUser(), angajat.getPassword());
    }
    public static Angajat getAngajat(CZbor.Request request){
        var angajat = request.getAngajat();
        return new Angajat(angajat.getUser(), angajat.getPassword());
    }

    public static Turist getTurist(CZbor.Response response){
        var turist = response.getTurist();
        return new Turist(turist.getNume());
    }
    public static Turist getTurist(CZbor.Request request){
        var turist = request.getTurist();
        return new Turist(turist.getNume());
    }

    public static List<Zbor> getZboruri(CZbor.Response response){
        var zboruri = response.getZboruriList();
        return zboruri.stream().map(zbor -> {
            try {
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                java.util.Date utilDate = format.parse(zbor.getDataPlecarii());
//                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                String dateString = zbor.getDataPlecarii().toString();

                // Define the date format of the input string
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy HH:mm:ss");

                    // Parse the string into a java.util.Date object
                    java.util.Date utilDate = dateFormat.parse(dateString);

                    // Convert java.util.Date to java.sql.Date
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());


                return new Zbor(zbor.getDestinatia(), sqlDate, zbor.getAeroport(), zbor.getNrLocuri());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
    }
    public static Zbor getZbor(CZbor.Request request){
        var zbor = request.getZbor();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = format.parse(zbor.getDataPlecarii());
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            return new Zbor(zbor.getDestinatia(), sqlDate, zbor.getAeroport(), zbor.getNrLocuri());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bilet getBilet(CZbor.Request request) {
        var bilet = request.getBilet();
        var angajat = bilet.getAngajat();
        var zbor = bilet.getZbor();
        var client = bilet.getClient();
        var listaTuristi = bilet.getListaTuristiList().stream().map(turist -> new Turist(turist.getNume())).collect(Collectors.toList());
        return new Bilet(new Angajat(angajat.getUser(), angajat.getPassword()), new Zbor(zbor.getDestinatia(), java.sql.Date.valueOf(zbor.getDataPlecarii()), zbor.getAeroport(), zbor.getNrLocuri()), new Turist(client.getNume()), listaTuristi, bilet.getAdresaClient(), bilet.getNrLocuri());
    }

//    public static Utilizator getUtilizator(CZbor.Response response){
//        var utilizator = response.getUtilizator();
//        return new Utilizator("", utilizator.getUsername(), utilizator.getPassword());
//    }
//
//    public static Utilizator getUtilizator(CZbor.Request request){
//        var utilizator = request.getUtilizator();
//        return new Utilizator("", utilizator.getUsername(), utilizator.getPassword());
//    }
//
//
//    public static RezervareDTO getRezervareDTO(CZbor.Request request){
//        var rezervare = request.getRezervare();
//        return new RezervareDTO(rezervare.getNumeClient(), rezervare.getTelefonClient(), rezervare.getUsername(), rezervare.getNumarLocuri());
//    }
//
//    public static List<ExcursieDTO> getExcursii(CZbor.Response response){
//        var excursii = response.getExcursiiList();
//        return excursii.stream().map(excursie -> new ExcursieDTO(excursie.getId(), excursie.getObiectivTuristic(), excursie.getFirmaTransport(), LocalTime.parse(excursie.getOraPlecarii()), excursie.getPret(), excursie.getNumarLocuriDisponibile())).toList();
//    }
//
//    public static FilterDTO getFilterDTO(CZbor.Request request){
//        var filter = request.getFilter();
//        return new FilterDTO(filter.getObiectivTuristic(), LocalTime.parse(filter.getDeLa()), LocalTime.parse(filter.getPanaLa()));
//    }


}