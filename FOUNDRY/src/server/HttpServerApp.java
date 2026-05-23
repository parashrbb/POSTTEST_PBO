package server;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import controller.PelangganController;
import controller.PesananController;
import model.Layanan;
import model.Pelanggan;
import model.Pesanan;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class HttpServerApp {
    // Ketua Dan Anggota
    private static final String Paras  = "Muhammad Farras Arhab Ince - 092";
    private static final String Wawan  = "Muhammad Kurniawan - 091";
    private static final String Kelpin = "Kelvin Alviansyah - 094";
    private static final String Mita   = "Syalomitha Novindriani Depe - 098";
    private static final String Isan   = "Ikhsan - 118";

    // ===== KREDENSIAL OWNER (hardcode) =====
    private static final String OWNER_USERNAME = "admin";
    private static final String OWNER_PASSWORD = "admin123";
    private static final String TOKEN          = "foundry-token-2025";

    // ===== SHARED CONTROLLER =====
    private final PelangganController pelangganCtrl = new PelangganController();
    private final PesananController   pesananCtrl   = new PesananController();

    // ===== Deteksi folder frontend secara otomatis =====
    // Ini fix bug: program bisa dijalankan dari direktori mana saja
    private static String frontendDir;
    static {
        // Coba cari folder frontend relatif terhadap lokasi JAR / working dir
        String[] candidates = {
            "frontend",
            "FOUNDRY/frontend",
            "../frontend",
            System.getProperty("user.dir") + "/frontend",
            System.getProperty("user.dir") + "/FOUNDRY/frontend"
        };
        frontendDir = "frontend"; // default
        for (String c : candidates) {
            if (new File(c).isDirectory()) {
                frontendDir = c;
                break;
            }
        }
    }

    public void startServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            // ===== ROUTING =====
            server.createContext("/",                        new FrontendHandler());
            server.createContext("/api/login",               new LoginHandler());
            server.createContext("/api/pelanggan",           new PelangganHandler());
            server.createContext("/api/pelanggan/cari",      new PelangganCariHandler());   // ← BARU: cariByNoHp
            server.createContext("/api/pesanan",             new PesananHandler());
            server.createContext("/api/pesanan/carinohp",    new PesananCariNoHpHandler()); // ← BARU: cari pesanan by noHp
            server.createContext("/api/pesanan/detail",      new PesananDetailHandler());   // ← BARU: getInfo(boolean)
            server.createContext("/api/layanan",             new LayananHandler());
            server.createContext("/api/stats",               new StatsHandler());            // ← BARU: isEmpty() + ringkasan

            server.setExecutor(null);
            server.start();

            System.out.println("================================================");
            System.out.println("  FOUNDRY LAUNDRY - SERVER BERJALAN");
            System.out.println("  Buka browser : http://localhost:8080");
            System.out.println("  Username     : " + OWNER_USERNAME);
            System.out.println("  Password     : " + OWNER_PASSWORD);
            System.out.println("  Frontend dir : " + new File(frontendDir).getAbsolutePath());
            System.out.println("================================================");
            System.out.println("  Nama Ketua  : " + Paras);
            System.out.println("  Anggota     : " + Wawan);
            System.out.println("  Anggota     : " + Kelpin);
            System.out.println("  Anggota     : " + Mita);
            System.out.println("  Anggota     : " + Isan);
            System.out.println("================================================");

        } catch (Exception e) {
            System.err.println("Terjadi kesalahan pada server: " + e.getMessage());
        }
    }

    // =========================================================
    // HANDLER: Serve file HTML/CSS dari folder frontend/
    // FIX: menggunakan frontendDir yang auto-detect
    // =========================================================
    class FrontendHandler implements com.sun.net.httpserver.HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) path = "/login.html";

            File file = new File(frontendDir + path);
            if (!file.exists()) {
                String resp = "404 Not Found: " + path;
                exchange.sendResponseHeaders(404, resp.length());
                exchange.getResponseBody().write(resp.getBytes());
                exchange.getResponseBody().close();
                return;
            }

            String contentType = "text/html; charset=UTF-8";
            if (path.endsWith(".css")) contentType = "text/css";
            if (path.endsWith(".js"))  contentType = "application/javascript";

            byte[] bytes = Files.readAllBytes(file.toPath());
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.getResponseBody().close();
        }
    }

    // =========================================================
    // HANDLER: /api/login
    // =========================================================
    class LoginHandler implements com.sun.net.httpserver.HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCors(exchange);
            if (exchange.getRequestMethod().equals("OPTIONS")) { exchange.sendResponseHeaders(204, -1); return; }
            if (!exchange.getRequestMethod().equals("POST"))   { exchange.sendResponseHeaders(405, -1); return; }

            String body     = bacaBody(exchange);
            String username = ambilNilai(body, "username");
            String password = ambilNilai(body, "password");

            if (OWNER_USERNAME.equals(username) && OWNER_PASSWORD.equals(password)) {
                String resp = "{\"sukses\":true,\"token\":\"" + TOKEN + "\",\"pesan\":\"Login berhasil. Selamat datang!\"}";
                kirimJson(exchange, 200, resp);
            } else {
                String resp = "{\"sukses\":false,\"token\":\"\",\"pesan\":\"Username atau password salah.\"}";
                kirimJson(exchange, 401, resp);
            }
        }
    }

    // =========================================================
    // HANDLER: /api/pelanggan
    // GET / POST / PUT / DELETE
    // =========================================================
    class PelangganHandler implements com.sun.net.httpserver.HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCors(exchange);
            if (exchange.getRequestMethod().equals("OPTIONS")) { exchange.sendResponseHeaders(204, -1); return; }
            if (!tokenValid(exchange)) { kirimJson(exchange, 401, "{\"pesan\":\"Unauthorized.\",\"sukses\":false}"); return; }

            String method = exchange.getRequestMethod();
            String response;

            if (method.equals("GET")) {
                List<Pelanggan> list = pelangganCtrl.getAll();
                StringBuilder sb = new StringBuilder("[");
                for (int i = 0; i < list.size(); i++) {
                    Pelanggan p = list.get(i);
                    // ===== GUNAKAN getKategori() via instanceof Transaksi =====
                    String kategori = "Biasa";
                    if (p instanceof interfaces.Transaksi) {
                        kategori = ((interfaces.Transaksi) p).getKategori();
                    }
                    // ===== GUNAKAN getDiskon() untuk PelangganVIP =====
                    double diskon = 0;
                    if (p instanceof model.PelangganVIP) {
                        diskon = ((model.PelangganVIP) p).getDiskon();
                    }
                    sb.append("{")
                      .append("\"id\":\"").append(p.getId()).append("\",")
                      .append("\"nama\":\"").append(escape(p.getNama())).append("\",")
                      .append("\"noHp\":\"").append(p.getNoHp()).append("\",")
                      .append("\"alamat\":\"").append(escape(p.getAlamat())).append("\",")
                      .append("\"status\":\"").append(escape(p.getStatus())).append("\",")
                      .append("\"kategori\":\"").append(kategori).append("\",")  // ← dari getKategori()
                      .append("\"diskon\":").append(diskon)                      // ← dari getDiskon()
                      .append("}");
                    if (i < list.size() - 1) sb.append(",");
                }
                sb.append("]");
                kirimJson(exchange, 200, sb.toString());

            } else if (method.equals("POST")) {
                String body   = bacaBody(exchange);
                String nama   = ambilNilai(body, "nama");
                String noHp   = ambilNilai(body, "noHp");
                String alamat = ambilNilai(body, "alamat");
                boolean isVIP = ambilNilai(body, "status").equalsIgnoreCase("VIP");

                String hasil   = pelangganCtrl.tambah(nama, noHp, alamat, isVIP);
                boolean sukses = hasil.startsWith("SUKSES");
                response = "{\"pesan\":\"" + escape(hasil) + "\",\"sukses\":" + sukses + "}";
                kirimJson(exchange, sukses ? 200 : 400, response);

            } else if (method.equals("PUT")) {
                String body   = bacaBody(exchange);
                String id     = ambilNilai(body, "id");
                String nama   = ambilNilai(body, "nama");
                String noHp   = ambilNilai(body, "noHp");
                String alamat = ambilNilai(body, "alamat");

                String hasil   = pelangganCtrl.update(id, nama, noHp, alamat);
                boolean sukses = hasil.startsWith("SUKSES");
                response = "{\"pesan\":\"" + escape(hasil) + "\",\"sukses\":" + sukses + "}";
                kirimJson(exchange, sukses ? 200 : 400, response);

            } else if (method.equals("DELETE")) {
                String query = exchange.getRequestURI().getQuery();
                String id    = (query != null) ? query.replace("id=", "") : "";

                String hasil   = pelangganCtrl.hapus(id);
                boolean sukses = hasil.startsWith("SUKSES");
                response = "{\"pesan\":\"" + escape(hasil) + "\",\"sukses\":" + sukses + "}";
                kirimJson(exchange, sukses ? 200 : 400, response);

            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    // =========================================================
    // HANDLER: /api/pelanggan/cari?noHp=...
    // ===== IMPLEMENTASI cariByNoHp() =====
    // Dipakai di halaman pesanan: owner cari pelanggan by nomor HP
    // untuk kemudian langsung buat pesanan / lihat pesanan terkait
    // =========================================================
    class PelangganCariHandler implements com.sun.net.httpserver.HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCors(exchange);
            if (exchange.getRequestMethod().equals("OPTIONS")) { exchange.sendResponseHeaders(204, -1); return; }
            if (!tokenValid(exchange)) { kirimJson(exchange, 401, "{\"pesan\":\"Unauthorized.\",\"sukses\":false}"); return; }

            String query = exchange.getRequestURI().getQuery();
            String noHp  = "";
            if (query != null) {
                for (String param : query.split("&")) {
                    if (param.startsWith("noHp=")) {
                        noHp = URLDecoder.decode(param.substring(5), StandardCharsets.UTF_8);
                    }
                }
            }

            // ===== GUNAKAN cariByNoHp() =====
            Pelanggan p = pelangganCtrl.cariByNoHp(noHp);
            if (p == null) {
                kirimJson(exchange, 404, "{\"sukses\":false,\"pesan\":\"Pelanggan dengan No HP " + noHp + " tidak ditemukan.\"}");
                return;
            }

            String kategori = (p instanceof interfaces.Transaksi) ? ((interfaces.Transaksi) p).getKategori() : "Biasa";
            double diskon   = (p instanceof model.PelangganVIP)   ? ((model.PelangganVIP) p).getDiskon()    : 0;

            String resp = "{\"sukses\":true," +
                    "\"id\":\"" + p.getId() + "\"," +
                    "\"nama\":\"" + escape(p.getNama()) + "\"," +
                    "\"noHp\":\"" + p.getNoHp() + "\"," +
                    "\"alamat\":\"" + escape(p.getAlamat()) + "\"," +
                    "\"status\":\"" + escape(p.getStatus()) + "\"," +
                    "\"kategori\":\"" + kategori + "\"," +
                    "\"diskon\":" + diskon + "}";
            kirimJson(exchange, 200, resp);
        }
    }

    // =========================================================
    // HANDLER: /api/pesanan
    // GET / POST / PUT / DELETE
    // =========================================================
    class PesananHandler implements com.sun.net.httpserver.HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCors(exchange);
            if (exchange.getRequestMethod().equals("OPTIONS")) { exchange.sendResponseHeaders(204, -1); return; }
            if (!tokenValid(exchange)) { kirimJson(exchange, 401, "{\"pesan\":\"Unauthorized.\",\"sukses\":false}"); return; }

            String method = exchange.getRequestMethod();
            String response;

            if (method.equals("GET")) {
                List<Pesanan> list = pesananCtrl.getAll();
                StringBuilder sb = new StringBuilder("[");
                for (int i = 0; i < list.size(); i++) {
                    Pesanan p = list.get(i);
                    sb.append("{")
                      .append("\"id\":\"").append(p.getId()).append("\",")
                      .append("\"namaPelanggan\":\"").append(escape(p.getNamaPelanggan())).append("\",")
                      .append("\"statusPelanggan\":\"").append(escape(p.getStatusPelanggan())).append("\",")
                      .append("\"namaLayanan\":\"").append(escape(p.getNamaLayanan())).append("\",")
                      .append("\"berat\":").append(p.getBerat()).append(",")
                      .append("\"hargaPerKg\":").append(p.getHargaPerKg()).append(",")
                      .append("\"totalHarga\":").append(p.getTotalHarga()).append(",")
                      .append("\"selesai\":").append(p.isSelesai()).append(",")
                      // ===== GUNAKAN getStatusKerjaan() =====
                      .append("\"statusKerjaan\":\"").append(escape(p.getStatusKerjaan())).append("\"")
                      .append("}");
                    if (i < list.size() - 1) sb.append(",");
                }
                sb.append("]");
                kirimJson(exchange, 200, sb.toString());

            } else if (method.equals("POST")) {
                String body        = bacaBody(exchange);
                String idPelanggan = ambilNilai(body, "idPelanggan");
                String idLayanan   = ambilNilai(body, "idLayanan");
                double berat;
                try { berat = Double.parseDouble(ambilNilai(body, "berat")); }
                catch (NumberFormatException e) { kirimJson(exchange, 400, "{\"sukses\":false,\"pesan\":\"Berat tidak valid.\"}"); return; }

                Pelanggan pelanggan = pelangganCtrl.cariById(idPelanggan);
                String hasil        = pesananCtrl.tambah(pelanggan, idLayanan, berat);
                boolean sukses      = hasil.startsWith("SUKSES");
                response = "{\"pesan\":\"" + escape(hasil.split("\\|")[0]) + "\",\"sukses\":" + sukses + "}";
                kirimJson(exchange, sukses ? 200 : 400, response);

            } else if (method.equals("PUT")) {
                String body     = bacaBody(exchange);
                String id       = ambilNilai(body, "id");
                boolean selesai = ambilNilai(body, "selesai").equals("true");

                String hasil   = pesananCtrl.updateStatus(id, selesai);
                boolean sukses = hasil.startsWith("SUKSES");
                response = "{\"pesan\":\"" + escape(hasil) + "\",\"sukses\":" + sukses + "}";
                kirimJson(exchange, sukses ? 200 : 400, response);

            } else if (method.equals("DELETE")) {
                String query = exchange.getRequestURI().getQuery();
                String id    = (query != null) ? query.replace("id=", "") : "";

                String hasil   = pesananCtrl.hapus(id);
                boolean sukses = hasil.startsWith("SUKSES");
                response = "{\"pesan\":\"" + escape(hasil) + "\",\"sukses\":" + sukses + "}";
                kirimJson(exchange, sukses ? 200 : 400, response);

            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    // =========================================================
    // HANDLER: /api/pesanan/carinohp?noHp=...
    // ===== IMPLEMENTASI cariByNoHp() di konteks pesanan =====
    // Owner masukkan nomor HP pelanggan → tampil semua pesanan miliknya
    // → langsung bisa klik "Selesaikan" tanpa cari manual di tabel
    // =========================================================
    class PesananCariNoHpHandler implements com.sun.net.httpserver.HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCors(exchange);
            if (exchange.getRequestMethod().equals("OPTIONS")) { exchange.sendResponseHeaders(204, -1); return; }
            if (!tokenValid(exchange)) { kirimJson(exchange, 401, "{\"pesan\":\"Unauthorized.\",\"sukses\":false}"); return; }

            String query = exchange.getRequestURI().getQuery();
            String noHp  = "";
            if (query != null) {
                for (String param : query.split("&")) {
                    if (param.startsWith("noHp=")) {
                        noHp = URLDecoder.decode(param.substring(5), StandardCharsets.UTF_8);
                    }
                }
            }

            // ===== GUNAKAN cariByNoHp() =====
            Pelanggan pelanggan = pelangganCtrl.cariByNoHp(noHp);
            if (pelanggan == null) {
                kirimJson(exchange, 404, "{\"sukses\":false,\"pesan\":\"Pelanggan dengan No HP " + noHp + " tidak ditemukan.\",\"pesanan\":[]}");
                return;
            }

            // Kumpulkan semua pesanan milik pelanggan ini (berdasarkan nama)
            List<Pesanan> semuaPesanan = pesananCtrl.getAll();
            StringBuilder sb = new StringBuilder();
            sb.append("{\"sukses\":true,")
              .append("\"pelanggan\":{")
              .append("\"id\":\"").append(pelanggan.getId()).append("\",")
              .append("\"nama\":\"").append(escape(pelanggan.getNama())).append("\",")
              .append("\"noHp\":\"").append(pelanggan.getNoHp()).append("\",")
              .append("\"status\":\"").append(escape(pelanggan.getStatus())).append("\"")
              .append("},")
              .append("\"pesanan\":[");

            int count = 0;
            for (Pesanan p : semuaPesanan) {
                if (p.getNamaPelanggan().equals(pelanggan.getNama())) {
                    if (count > 0) sb.append(",");
                    sb.append("{")
                      .append("\"id\":\"").append(p.getId()).append("\",")
                      .append("\"namaLayanan\":\"").append(escape(p.getNamaLayanan())).append("\",")
                      .append("\"berat\":").append(p.getBerat()).append(",")
                      .append("\"totalHarga\":").append(p.getTotalHarga()).append(",")
                      .append("\"selesai\":").append(p.isSelesai()).append(",")
                      .append("\"statusKerjaan\":\"").append(escape(p.getStatusKerjaan())).append("\",")
                      // ===== GUNAKAN getInfo(boolean) - detail pesanan =====
                      .append("\"detail\":\"").append(escape(p.getInfo(true))).append("\"")
                      .append("}");
                    count++;
                }
            }
            sb.append("]}");
            kirimJson(exchange, 200, sb.toString());
        }
    }

    // =========================================================
    // HANDLER: /api/pesanan/detail?id=...
    // ===== IMPLEMENTASI getInfo(boolean) - overloading =====
    // Tampilkan detail lengkap satu pesanan termasuk harga/kg & status pelanggan
    // =========================================================
    class PesananDetailHandler implements com.sun.net.httpserver.HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCors(exchange);
            if (exchange.getRequestMethod().equals("OPTIONS")) { exchange.sendResponseHeaders(204, -1); return; }
            if (!tokenValid(exchange)) { kirimJson(exchange, 401, "{\"pesan\":\"Unauthorized.\",\"sukses\":false}"); return; }

            String query = exchange.getRequestURI().getQuery();
            String id    = "";
            if (query != null) {
                for (String param : query.split("&")) {
                    if (param.startsWith("id=")) id = URLDecoder.decode(param.substring(3), StandardCharsets.UTF_8);
                }
            }

            Pesanan p = pesananCtrl.cariById(id);
            if (p == null) {
                kirimJson(exchange, 404, "{\"sukses\":false,\"pesan\":\"Pesanan tidak ditemukan.\"}");
                return;
            }

            // ===== GUNAKAN getInfo(boolean) — overloading method =====
            String detailLengkap = p.getInfo(true);

            String resp = "{\"sukses\":true," +
                    "\"id\":\"" + p.getId() + "\"," +
                    "\"namaPelanggan\":\"" + escape(p.getNamaPelanggan()) + "\"," +
                    "\"statusPelanggan\":\"" + escape(p.getStatusPelanggan()) + "\"," +
                    "\"namaLayanan\":\"" + escape(p.getNamaLayanan()) + "\"," +
                    "\"hargaPerKg\":" + p.getHargaPerKg() + "," +
                    "\"berat\":" + p.getBerat() + "," +
                    "\"totalHarga\":" + p.getTotalHarga() + "," +
                    "\"selesai\":" + p.isSelesai() + "," +
                    "\"statusKerjaan\":\"" + escape(p.getStatusKerjaan()) + "\"," +
                    "\"detailLengkap\":\"" + escape(detailLengkap) + "\"}";
            kirimJson(exchange, 200, resp);
        }
    }

    // =========================================================
    // HANDLER: /api/layanan — GET saja
    // =========================================================
    class LayananHandler implements com.sun.net.httpserver.HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCors(exchange);
            if (exchange.getRequestMethod().equals("OPTIONS")) { exchange.sendResponseHeaders(204, -1); return; }
            if (!tokenValid(exchange)) { kirimJson(exchange, 401, "{\"pesan\":\"Unauthorized.\",\"sukses\":false}"); return; }

            List<Layanan> list = pesananCtrl.getAllLayanan();
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++) {
                Layanan l = list.get(i);
                sb.append("{")
                  .append("\"id\":\"").append(l.getId()).append("\",")
                  .append("\"nama\":\"").append(escape(l.getNama())).append("\",")
                  .append("\"hargaPerKg\":").append(l.getHargaPerKg())
                  .append("}");
                if (i < list.size() - 1) sb.append(",");
            }
            sb.append("]");
            kirimJson(exchange, 200, sb.toString());
        }
    }

    // =========================================================
    // HANDLER: /api/stats
    // ===== IMPLEMENTASI isEmpty() dari kedua controller =====
    // Dipakai di dashboard untuk tampilkan ringkasan + peringatan
    // =========================================================
    class StatsHandler implements com.sun.net.httpserver.HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCors(exchange);
            if (exchange.getRequestMethod().equals("OPTIONS")) { exchange.sendResponseHeaders(204, -1); return; }
            if (!tokenValid(exchange)) { kirimJson(exchange, 401, "{\"pesan\":\"Unauthorized.\",\"sukses\":false}"); return; }

            // ===== GUNAKAN isEmpty() dari kedua controller =====
            boolean pelangganKosong = pelangganCtrl.isEmpty();
            boolean pesananKosong   = pesananCtrl.isEmpty();

            List<model.Pelanggan> pelangganList = pelangganCtrl.getAll();
            List<model.Pesanan>   pesananList   = pesananCtrl.getAll();

            int totalPelanggan = pelangganList.size();
            int totalPesanan   = pesananList.size();
            int pesananSelesai = 0;
            int pesananPending = 0;
            double totalPendapatan = 0;

            for (Pesanan p : pesananList) {
                if (p.isSelesai()) {
                    pesananSelesai++;
                    totalPendapatan += p.getTotalHarga();
                } else {
                    pesananPending++;
                }
            }

            int pelangganVIP   = 0;
            int pelangganBiasa = 0;
            for (Pelanggan p : pelangganList) {
                if (p instanceof model.PelangganVIP) pelangganVIP++;
                else pelangganBiasa++;
            }

            String resp = "{" +
                    "\"sukses\":true," +
                    "\"totalPelanggan\":" + totalPelanggan + "," +
                    "\"pelangganVIP\":" + pelangganVIP + "," +
                    "\"pelangganBiasa\":" + pelangganBiasa + "," +
                    "\"pelangganKosong\":" + pelangganKosong + "," +     // ← isEmpty()
                    "\"totalPesanan\":" + totalPesanan + "," +
                    "\"pesananSelesai\":" + pesananSelesai + "," +
                    "\"pesananPending\":" + pesananPending + "," +
                    "\"pesananKosong\":" + pesananKosong + "," +         // ← isEmpty()
                    "\"totalPendapatan\":" + (long)totalPendapatan +
                    "}";
            kirimJson(exchange, 200, resp);
        }
    }

    // =========================================================
    // HELPER METHODS
    // =========================================================
    private String bacaBody(HttpExchange exchange) throws IOException {
        return new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    }

    private String ambilNilai(String json, String key) {
        String cariStr = "\"" + key + "\":\"";
        int start = json.indexOf(cariStr);
        if (start != -1) {
            start += cariStr.length();
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        }
        String cariNum = "\"" + key + "\":";
        start = json.indexOf(cariNum);
        if (start == -1) return "";
        start += cariNum.length();
        int end = json.indexOf(",", start);
        if (end == -1) end = json.indexOf("}", start);
        return json.substring(start, end).trim();
    }

    private boolean tokenValid(HttpExchange exchange) {
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authHeader == null) return false;
        return authHeader.equals("Bearer " + TOKEN);
    }

    private void kirimJson(HttpExchange exchange, int status, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
    }

    private void setCors(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin",  "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
    }
}
