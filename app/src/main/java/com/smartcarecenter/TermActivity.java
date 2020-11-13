package com.smartcarecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class TermActivity extends AppCompatActivity {
    TextView mContent;
    String content_details = "";
    LinearLayout mback;
    WebView mviewweb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        mContent = findViewById(R.id.termcontent);
        mback = findViewById(R.id.backbtn);
        mviewweb = findViewById(R.id.viewterms);
//        content_details = "<p><strong><u>TERMS AND CONDITIONS</u></strong></p>\n" +
//                "<p>&nbsp;</p>\n" +
//                "<p>Smart Care Center adalah aplikasi online yang dimiliki dan dioperasikan oleh PT Samafitro divisi BCS. Segala aktifitas dan aturan yang bersangkutan langsung dengan aplikasi ini akan sepenuhnya diatur oleh syarat dan ketentuan di bawah ini.</p>\n" +
//                "<p>&nbsp;</p>\n" +
//                "<p>Dalam waktu yang tidak ditentukan syarat dan ketentuan akan berubah. Penggunaan anda atas aplikasi ini harus mengikuti setiap perubahan yang merupakan bagian dari perjanjian dan terikat oleh persyaratan sesuai dengan perubahan yang ada tanpa terkecuali.</p>\n" +
//                "<p><strong>&nbsp;</strong></p>\n" +
//                "<p><strong><u>Barang dan Jasa</u></strong></p>\n" +
//                "<p><strong>&nbsp;</strong></p>\n" +
//                "<p><strong>PT Samafitro merupakan perusahaan yang memperjual belikan barang / jasa after sales mesin Digital Printing HP Indigo</strong></p>\n" +
//                "<p>&nbsp;</p>\n" +
//                "<p><strong><u>Kebijakan Pemesanan</u></strong></p>\n" +
//                "<p>&nbsp;</p>\n" +
//                "<ul>\n" +
//                "<li>Pelanggan terlebih dahulu memilih produk yang diinginkan dengan meng-klik &ldquo;Add Item&rdquo;</li>\n" +
//                "<li>Setelah pesanan masuk ke keranjang belanja, pelanggan dapat memilih untuk menambah pesanan belanja atau submit order untuk proses selanjutnya</li>\n" +
//                "<li>Dalam proses checkout pelanggan diminta untuk memilih metode pembayaran</li>\n" +
//                "</ul>\n" +
//                "<p>&nbsp;</p>\n" +
//                "<p><strong><u>Kebijakan pengembalian barang, pengembalian dana (refund) dan pembatalan (cancellation) </u></strong></p>\n" +
//                "<p>&nbsp;</p>\n" +
//                "<p>&nbsp;</p>\n" +
//                "<p>Kami tidak melayani pengembalian barang kecuali apabila ada kesalahan dari pihak kami dan di luar dari yang sudah ditetapkan dalan syarat dan ketentuan pada halaman ini. Pengembalian barang yang terjadi di luar dari kesepakatan penyelesain masalah pembelian yang telah disetujui kedua belah pihak bukan merupakan tanggung jawab dan bagian dari pelayanan kami. Kami tidak bertanggung jawab atas barang yang anda kembalikan karena secara sah barang tersebut sudah menjadi hak milik dan tanggung jawab anda.</p>\n" +
//                "<p>&nbsp;</p>\n" +
//                "<ul>\n" +
//                "<li>Return berlaku apabila barang yang di terima dalam keadaan rusak/cacat/aksesoris tidak lengkap.</li>\n" +
//                "<li>Refund berlaku apabila barang yang telah di pesan dan dibayar tidak ada stock.</li>\n" +
//                "<li><strong>Pembatalan Pesanan (cancelation)</strong> <strong>dibatalkan secara sepihak apabila:</strong></li>\n" +
//                "<li><strong>Anda belum melakukan pembayaran</strong> dalam waktu 3x24 jam setelah order atau atas permintaan pelanggan</li>\n" +
//                "<li><strong>Kami belum menerima pembayaran anda</strong></li>\n" +
//                "<li><strong>Tanggal berlaku pada invoice untuk order tersebut sudah kadaluarsa</strong></li>\n" +
//                "</ul>\n" +
//                "<p>&nbsp;</p>\n" +
//                "<p><strong><u>Kebijakan Privasi</u></strong></p>\n" +
//                "<p>&nbsp;</p>\n" +
//                "<p>Kami di PT Samafitro menjaga privasi Anda dengan sangat serius. Kami percaya bahwa privasi elektronik sangat penting bagi keberhasilan berkelanjutan dari Internet.<br /> Kami percaya bahwa informasi ini hanya dan harus digunakan untuk membantu kami menyediakan layanan yang lebih baik. Itulah sebabnya kami telah menempatkan kebijakan untuk melindungi informasi pribadi Anda.<br /> <br /> Ringkasan Kebijakan kami<br /> Secara umum, ketika Anda mengunjungi dan mengakses aplikasi kami, informasi Anda akan tetap sebagai anonim. Sebelum kami meminta Anda untuk mengisi informasi, kami akan menjelaskan bagaimana informasi ini akan digunakan. Kami tidak akan memberikan informasi pribadi Anda kepada perusahaan lain atau individu tanpa se-izin Anda.<br /> <br /> Beberapa bagian dari website kami memerlukan pendaftaran untuk mengaksesnya, walaupun biasanya semua yang diminta adalah berupa alamat e-mail dan beberapa informasi dasar tentang Anda.<br /> <br /> Ada bagian di mana kami akan meminta informasi tambahan. Kami melakukan ini untuk dapat lebih memahami kebutuhan Anda, dan memberikan Anda palayanan yang kami percaya mungkin berharga bagi Anda. Beberapa contoh informasi website kami butuhkan seperti nama, email, alamat rumah, dan info pribadi. Kami memberikan Anda kesempatan untuk memilih untuk tidak menerima materi informasi dari kami.</p>\n" +
//                "<p>&nbsp;</p>\n" +
//                "<p>Melindungi privasi Anda<br /> Kami akan mengambil langkah yang tepat untuk melindungi privasi Anda. Setiap kali Anda memberikan informasi yang sensitif (misalnya, nomor kartu kredit untuk melakukan pembelian), kami akan mengambil langkah-langkah yang wajar untuk melindungi, seperti enkripsi nomor kartu Anda. Kami juga akan mengambil langkah-langkah keamanan yang wajar untuk melindungi informasi pribadi Anda dalam penyimpanan. <strong>Nomor kartu kredit hanya digunakan untuk proses pembayaran dan bukan disimpan untuk tujuan pemasaran.</strong></p>\n" +
//                "<p><strong><br /> Kami tidak akan memberikan informasi pribadi Anda kepada perusahaan lain atau individu tanpa izin Anda</strong>.<strong><u> \"Proses order anda kami pastikan aman dengan protokol&nbsp; Secure Sockets Layer (SSL) dimana SSL menyediakan setiap pelanggan keamanan penuh dan kebebasan untuk belanja online tanpa rasa khawatir mengenai kemungkinan pencurian informasi&nbsp; kartu kredit\" </u></strong></p>\n" +
//                "<p>&nbsp;</p>\n" +
//                "<p><strong><u>Masukan, Saran, dan Pesan</u></strong></p>\n" +
//                "<p><strong>&nbsp;</strong></p>\n" +
//                "<p>&nbsp;</p>\n" +
//                "<p>Perkembangan dan peningkatan pelayanan kami tidak terlepas dari adanya masukan dari anda. Jika anda ingin memberi masukan, saran, atau pesan, Anda dapat menghubungi care center kami di +628111930199 atau dengan mudah meng-klik &ldquo;Live Chat&rdquo;.</p>\n" +
//                "<p>&nbsp;</p>\n" +
//                "<p><strong>Jam Operasional</strong></p>\n" +
//                "<p>&nbsp;</p>\n" +
//                "<p><strong>Kami akan memproses formulir konfirmasi pembayaran pada hari dan waktu operasional kantor PT Samafitro yaitu Senin-Jumat jam 08.00-17.00.</strong></p>";

        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mviewweb.loadUrl("http://smartcarecenter.id/docs/terms-conds.html");
        mviewweb.clearHistory();
        mviewweb.clearFormData();
        mviewweb.clearCache(true);
//        getHTML("http://smartcarecenter.id/docs/terms-conds.html");
//        try {
//            content_details = Jsoup.connect("http://smartcarecenter.id/docs/terms-conds.html").get().html();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        if (Build.VERSION.SDK_INT >= 24) {
            mContent.setText((CharSequence) Html.fromHtml((String)content_details, Html.FROM_HTML_MODE_COMPACT));
            mContent.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            mContent.setText((CharSequence)Html.fromHtml((String)content_details));
            mContent.setMovementMethod(LinkMovementMethod.getInstance());

        }
    }
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, Dashboard.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
    public String getHTML(String urlToRead) {
        URL google = null;
        try {
            google = new URL(urlToRead);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(google.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String input = null;
        StringBuffer stringBuffer = new StringBuffer();
        while (true)
        {
            try {
                if (!((input = in.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringBuffer.append(input);
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        content_details = stringBuffer.toString();
        return input;
    }

}