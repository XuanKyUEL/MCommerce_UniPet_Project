package com.unipet7.mcommerce.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.BlogAdapter;
import com.unipet7.mcommerce.adapters.ProductAdapter;
import com.unipet7.mcommerce.databinding.ActivityBlogDetailsBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Blogs;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.utils.Constants;
import com.unipet7.mcommerce.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

public class BlogDetails extends AppCompatActivity {
    ActivityBlogDetailsBinding binding;
    BlogAdapter blogAdapter;
    ProductAdapter adapter;

    FireStoreClass fireStoreClass = new FireStoreClass();
    public ArrayList<Product> blogProducts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogDetailsBinding.inflate(getLayoutInflater());
        loadProduct();
        addEvents();
        loadBlogData();
        initBlog();
        setContentView(binding.getRoot());
    }

    private void loadBlogData() {
        Blogs blogs = (Blogs) getIntent().getSerializableExtra(Constants.BLOG);
        ImageView imgBlog = binding.imvBlogDetailImage;
        imgBlog.setImageResource(blogs.getPic());
        binding.txtBlogDetailTitle.setText(blogs.getTitle());
        if (blogs.getTitle().equals("Top 7 giống chõ dễ nui nhất")) {
            binding.txtBlogDetailContent.setText("1. Chó Chihuahua  Chihuahua là giống chó đang được nuôi khá nhiều tại Việt Nam. Lý do dòng chó này được yêu chuộng một cách rộng rãi đó là bởi chúng có vóc dáng nhỏ nhắn, nhanh nhẹn, thông minh lại canh giữ rất tốt. Đầu ngắn với đôi tai dài cùng mắt lồi lớn. Một em chó Chihuahua thuần chủng thường trọng lượng khoảng 1-3 kg và cao 16-20 cm.  Mức giá của một em Chihuahua thường từ 3,5 đến 5 triệu đồng và em Chihuahua thuần chủng vào khoảng 1 - 2 triệu đồng.  2. Chó Bắc Kinh  Chó Bắc Kinh là giống chó được nuôi rất nhiều tại nước ta vì ngoại hình xinh yêu và dễ thương. Giống chó Bắc Kinh còn có 2 loại khác nữa là thuần chủng và lai Nhật. Đa phần chúng có lớp lông một màu, mõm ngắn và mũi to. Giá của chó Bắc Kinh tương tự với giống Chihuahua.  3. Chó Poodle  Poodle là giống chó có vẻ ngoài rất đáng yêu. Chúng muốn sự che chở và bảo vệ của chủ nhân.  Poodle có phần đầu tròn và nhỏ, lông xoăn xù, đuôi ngắn. Màu lông cơ bản nhất là nâu và cà phê. Giống chó này có tính cách mạnh mẽ, linh hoạt và dễ dàng điều khiển.  4. Chó Pug  Giống chó này được người yêu mến không hẳn nhờ đôi mắt to hay lớp lông dày chính là vì gương mặt tròn dễ thương của nó. Chó Pug có đầu và mắt tròn, da mặt lõm từng lớp khá đặc trưng. Màu lông thường là màu đen, nâu sẫm và vàng đậm. Pug khá dễ thương, vui vẻ và thân thiện.  Tuỳ theo xuất xứ, độ tuổi và ngoại hình, ... mà giá Pug mặt xệ rất đắt từ 7 đến 8 triệu cho một chú chó trong nước, đối với chó nhập khẩu thì mức giá này có khi sẽ cao lên tới gấp 3 đến 4 lần.  5. Chó lạp xưởng/xúc xích  Chúng có tên gọi là lạp xưởng là bởi vì thân hình của chú chó này cực kỳ đáng yêu và ngộ nghĩnh. Thay vì cao to, vạm vỡ những chú chó này lại có một thân hình dài, đôi chân ngắn, tai nhỏ và cụp.  Giống chó này được rất nhiều gia đình chọn lựa làm thú cưng bởi vì tính cách vui vẻ, dễ nuôi và hiền lành. Vàng và nâu là hai màu lông phổ biến nhất của giống chó này.  Tại Việt Nam, chó sinh ra không có giấy tờ nguồn gốc giá từ 2 triệu đến 3 triệu cho 1 bé. Chó có giấy tờ VKA (giấy công nhận chất lượng giống chó) giá từ 4 triệu đến 6 triệu cho 1 bé và trên 7 triệu là giống chó tốt và đầy đủ giấy tờ nguồn gốc xuất xứ.");
        } else if (blogs.getTitle().equals("5 tiêu chuẩn thức ăn cho mèo mà một Sen chính hiệu cần biết")) {
            binding.txtBlogDetailContent.setText("1. Giảm lượng tinh bột trong khẩu phần ăn mỗi ngày  Đúng rằng con người không thể sống thiếu tinh bột mỗi ngày, tuy nhiên bạn phải biết không chó là giống động vật ăn thịt chính, nhưng mèo thì là loài ăn thịt hoàn toàn.  Dẫu có những khác biệt đó, nếu gộp lại bạn chẳng thể bắt chó mèo ăn chay, hoặc cắt giảm toàn bộ lượng đạm và thay thế cho tinh bột được.  Ngoài ra, việc chó mèo dùng rất nhiều tinh bột cho một bữa ăn sẽ làm chúng gia tăng khả năng béo phì.  2.Mua pate tươi/pate đóng lon  Khi mua pate tươi sống bạn nên chọn những loại có chỉ số chất lượng và tiêu chuẩn thành phần rõ ràng trên bao bì. Bạn hãy căn cứ vào đó mà chọn lựa cho mình các thực phẩm phù hợp nhất với cún và mèo của mình. Ngoài ra, đây cũng là cơ sở các chuyên gia dinh dưỡng tư vấn giúp bạn những món đồ phù hợp với cún và mèo.  3\uFE0F.Lựa chọn các sản phẩm có tiêu chuẩn sản xuất thức ăn cho người  Nhiều người của Việt Nam hiện nay vẫn quan niệm thức ăn cho cún nuôi chỉ để chúng sống khỏe mạnh là được và không muốn bận tâm về cách chế biến hay nguồn gốc thực phẩm. Tuy nhiên nó lại là nhân tố quyết định sức khỏe của cún cưng.  Các thực phẩm có xuất xứ rõ ràng và cách đóng gói bao bì đúng tiêu chuẩn thức ăn cho con người sẽ làm cho cún nuôi tận hưởng được chất lượng sống cao hơn nữa.  4.Tham khảo ý kiến bác sĩ và cho ăn theo nhu cầu.  Đối với mèo đang mang thai & cho con bú, có cân nặng ngoài bảng tham khảo hoặc đang ở những giai đoạn sức khỏe đặc biệt cần chăm sóc chúng một cách kỹ lưỡng hơn, cần đưa chúng đến các cơ sở y tế để nhận được sự tư vấn của bác sĩ có chuyên môn.  5.Đảm bảo các chất dinh dưỡng cần thiết cho mèo trong khẩu phần ăn   Chất béo và axit béo: omega-3, omega-6 giúp hấp thụ các vitamin tan trong chất béo, tham gia bảo vệ, duy trì các cơ quan nội tạng.  Các loại vitamin và enzyme: vitamin kết hợp cùng với khoáng chất, enzyme tham gia vào trong quá trình trao đổi chất cho cơ thể, giúp làm đẹp da lông cho mèo.  Canxi là chất rất quan trọng trong việc cấu trúc và duy trì xương mèo luôn chắc khỏe  Taurine là một axit amin cần thiết cho mèo, giúp mèo điều hòa nhịp tim, tiêu hóa và khả năng sinh sản. Việc thiếu taurine ở mèo có thể gây thoái hóa võng mạc trung tâm, dẫn đến mù lòa không hồi phục, cũng như suy tim.");
        } else if (blogs.getTitle().equals("Cách xử lý vết thương khi bị chó cắn")) {
            binding.txtBlogDetailContent.setText("Hiện nay bệnh dại chưa có thuốc điều trị đặc hiệu. Xử lý vết thương khi bị chó cắn đúng cách và được tiêm vắc-xin phòng dại kịp thời là việc làm cần thiết để bảo vệ tính mạng khi ai đó bị chó, mèo cắn. 1. Phân loại mức độ các vết chó cắn Thông thường, sự nghiêm trọng của vết chó cắn sẽ được phân thành 5 mức độ khác nhau, cụ thể là: Mức độ 1: Răng của chó không chạm vào da. Mức độ 2: Răng của chó chạm vào da, nhưng da vẫn chưa rách. Mức độ 3: Có từ một đến bốn vết thương hở, nông trên da. Mức độ 4: Một vết cắn nhưng gây ra từ một đến bốn vết thương hở. Trong đó có ít nhất một vết thương thủng sâu. Mức độ 5: Nhiều vết cắn, bao gồm một số vết thương thủng sâu. Có thể do bị chó tấn công mạnh bạo. 2. Sơ cứu khi bị chó cắn chảy máu Khi bị chó tấn công, răng cửa của chúng sẽ ngoạm vào phần mô thịt, đồng thời những chiếc răng nhỏ hơn có thể làm rách da. Kết quả là gây nên một vết thương hở và lởm chởm. Chính vì vậy, khi bị chó cắn, bạn nên thực hiện ngay những bước sơ cứu để xử lý vết thương nhằm hạn chế khả năng nhiễm trùng: Đầu tiên, cần phải nhanh chóng kiểm tra vết thương. Nếu vết thương không chảy máu, hãy rửa thật sạch vùng da đó bằng xà phòng và nước ấm. Đối với tình trạng bị chó cắn chảy máu, cần chườm bằng vải sạch trong khoảng 5 phút hoặc cho đến khi máu ngừng chảy rồi mới rửa vết thương. Ấn nhẹ lên vết thương để một ít máu chảy ra, điều này sẽ giúp loại bỏ vi trùng Bôi kem kháng sinh lên vùng bị thương Sử dụng băng vô trùng để bịt kín vết thương Giữ vùng bị thương cao hơn tim để ngăn ngừa sưng và nhiễm trùng Trường hợp vết thương nhẹ ở mức độ 1, 2 hoặc 3 thì bạn có thể tự xử lý tại nhà một cách an toàn thông qua việc rửa vết thương hằng ngày và kiểm tra các dấu hiệu nhiễm trùng. 3. Khi nào cần gặp bác sĩ Sau khi thực hiện các biện pháp xử lý vết thương tại nhà, nếu nạn nhân có các biểu hiện sau đây thì nên đưa họ đến ngay cơ sở y tế gặp bác sĩ để được điều trị kịp thời: Máu chảy nhiều và không kiểm soát được Vết cắn để lộ xương, gân, cơ Vết thương gây đau dữ dội Gây mất chức năng, chẳng hạn như không thể uốn cong các ngón tay Có các dấu hiệu nhiễm trùng như sưng, nóng, đỏ Người bị sốt hoặc cảm thấy yếu ớt, ngất xỉu Vết thương tiết dịch mủ vàng và có mùi hôi Trên đây là những cách đơn giản nhất để xử lý khi bị chó cắn. Nếu bạn cần sự giúp đỡ, đừng ngại ngùng mà hãy cứ inbox trực tiếp cho chúng mình qua fanpage UniPet Store. Cuối cùng, hãy áp dụng ngay những phương pháp trên để tránh cho vết thương bị nhiễm trùng nhé!");
        } else if (blogs.getTitle().equals("Những lưu ý khi triệt sản chó cái")) {
            binding.txtBlogDetailContent.setText("1. Triệt sản là gì?\n" +
                    "Triệt sản (hay thiến) đây là một phẫu thuật loại bỏ cơ quan sinh dục của động vật. Việc này nhằm loại bỏ khả năng sinh sản của thú cưng một cách vĩnh viễn.\n" +
                    "Đối với con đực: Quá trình triệt sản là quá trình cắt bỏ tinh hoàn của chó mèo đực (hành động này thường được gọi là thiến).\n" +
                    "Đối với con cái: Quá trình triệt sản nhằm mục đích loại bỏ buồng trứng và tử cung\n" +
                    "Triệt sản chó cái không đơn giản như cuộc phẫu thuật triệt sản (thiến/hoạn) mà chó đực nhận được. Mà đó là cuộc phẫu thuật lớn hơn và cần dùng đến gây mê toàn thân.\n" +
                    "2. Triệt sản chó có lợi ích gì?\n" +
                    "Triệt sản cho chó cái mang lại nhiều lợi ích hơn bạn nghĩ đấy. Một số lợi ích như sau:\n" +
                    "Giúp chó cái tránh thai, chấm dứt nguy cơ chó mang thai ngoài ý muốn \n" +
                    "Giảm nguy cơ mắc các bệnh: Viêm tử cung tích mủ, ung thư tuyến vú…\n" +
                    "Tránh thu hút chó đực \n" +
                    "Loại bỏ một số mùi hôi trên người chó cái\n" +
                    "Giải quyết việc chó cái trở nên kích động trong thời kỳ động dục\n" +
                    "3. Nên triệt sản chó cái khi nào là phù hợp?\n" +
                    "Giải đáp cho câu hỏi “Triệt sản chó cái khi nào?” là: Những con chó cái khỏe mạnh, đủ khả năng phẫu thuật sẽ thực hiện phẫu thuật triệt sản trong độ tuổi từ khoảng 6-9 tháng là tốt nhất. Tuy nhiên chắc chắn hơn về thời gian triệt sản của chó cái. Chúng tôi khuyên bạn nên tìm hỏi ý kiến của bác sĩ thú y. Bác sĩ thú y sẽ xem xét thể trạng hiện tại của chó. Sau đó mới đưa ra những khuyến cáo về thời gian triệt sản thích hợp cho chú chó của bạn.\n" +
                    "Một lưu ý nữa về thời điểm triệt sản chó cái là tốt nhất nên làm trước chu kỳ dậy thì đầu tiên của chúng. Chu kỳ động dục đầu tiên của chó cái thường sẽ xảy ra vào khoảng 6-7 tháng tuổi. Nhiều bác sĩ thú y sẽ đợi đến khi chó cái gần sát tuổi “dậy thì” thì mới quyết định triệt sản. Bởi lúc đó chúng mới có khả năng chịu đựng được lượng thuốc gây mê cần thiết.\n" +
                    "Với chó có độ tuổi lớn hơn 6-9 tháng, việc triệt sản sẽ khó khăn hơn. Nếu bạn đã lỡ qua “thời điểm vàng” để triệt sản cho chó cái. Thì lần sau bạn sẽ phải tính toán chu kỳ động dục của nó trước khi triệt sản. Các bác sĩ thú y không khuyến khích bạn đưa chó đi triệt sản khi chúng vẫn còn đang trong thời kỳ động dục. Họ sẽ đợi từ 2-3 tháng, sau khi chu kỳ động dục của chó cái kết thúc rồi mới tiến hành phẫu thuật triệt sản.");
        } else {
            binding.txtBlogDetailContent.setText("Thú cưng không chỉ là một liều thuốc bổ cho tinh thần mà chúng còn dạy bạn rất nhiều bài học quý báu về cuộc sống.\n" +
                    "\n" +
                    "Lòng trung thành\n" +
                    "\n" +
                    "Cách mà thú cưng thể hiện lòng trung thành với những người xung quanh rất đơn giản như quấn quýt bên chân chúng ta, quẫy đuôi vui mừng khi chúng ta về…, tất cả những điều nhỏ nhặt ấy làm toát lên lòng trung thành của chúng.\n" +
                    "Lòng trung thành là một phẩm chất tốt của con người, nhưng đôi khi bạn chợt lãng quên nó vì cuộc sống bộn bề. Nghĩa hẹp của lòng trung thành đó là sự quan tâm đối với những người đã xuất hiện nâng đỡ bạn trong từng bước đi trong cuộc đời của bạn, hay nói cách khác là sự biết ơn, và cảm giác nhớ về và trân trọng. \n" +
                    "\n" +
                    "Cảm giác hài lòng với cuộc sống\n" +
                    "\n" +
                    "Cuộc sống của không ai trong chúng ta là hoàn hảo cả. Hãy biết chấp nhận để có thể sống một cách thoải mái nhất, như cách sống của những chú thú cưng, chỉ cần thức ăn, nước uống, nơi trú ẩn và tình yêu thương. Không nhiều nhưng vừa đủ. \n" +
                    "\n" +
                    "Tinh thần trách nhiệm\n" +
                    "\n" +
                    "Việc chăm sóc thú cưng giúp bạn rèn luyện được tinh thần trách nhiệm từ việc cho thú cưng ăn uống, đi ngủ, dọn dẹp chỗ ở cho chúng. Những trải nghiệm đó giúp bạn ngày càng hoàn thiện và trở thành một phiên bản tốt nhất của chính mình.\n" +
                    "\n" +
                    "Kiểm soát ngân sách \n" +
                    "\n" +
                    "Ngoài việc tự bỏ tiền mua vật nuôi thì việc chăm sóc chúng cũng ngốn của bạn không ít tiền. Đó là chưa tính những loài như: chó ngao, chó poodle, mèo Bengal... có giá cao chót vót. Vì vậy, trước khi quyết định mua bạn phải cân nhắc xem ngân sách của mình có đủ khả năng nuôi dưỡng chúng trong thời gian đó hay không. Chính điều này giúp bạn cách cân nhắc tiền nong trước khi đầu tư cho việc gì đó.\n" +
                    "\n" +
                    "Thành lập thói quen\n" +
                    "\n" +
                    "Nuôi một con chó hay con mèo, đồng nghĩa với việc bạn cần cho chúng ăn uống, tắm gội, đưa chúng đi chơi... Đây là những việc làm không thể thiếu khi chăm sóc vật nuôi. Qua một thời gian, việc này sẽ trở thành thói quen được thực hiện thường xuyên. Vô tình nó sẽ giúp bạn dần hoàn thiện những thói quen trong cuộc sống.\n" +
                    "\n" +
                    "Biết vượt qua nỗi đau\n" +
                    "\n" +
                    "Đến một ngày nào đó con vật mà bạn yêu thương cũng sẽ rời bỏ bạn với bất cứ lý do nào đó. Sống chung lâu ngày cũng có tình cảm và tất nhiên chúng ta sẽ đau khổ hơn khi mất đi vật yêu thương đó. Nhưng bạn phải chấp nhận điều này để có thể vui mừng và đón chào thú cưng mới. Chắc hẳn mỗi lúc như vậy, bạn sẽ học được cách vượt lên nỗi đau mắc phải trong cuộc sống.");
        }
    }

    private void loadProduct() {
        LoadingDialog ldDialog1 = new LoadingDialog();
        ldDialog1.showLoadingDialog(this);
        fireStoreClass.getRandomProductsBlog(this, blogProducts, 10);
        ldDialog1.dissmis();
    }
    public void configAdaptersBlog() {
        adapter = new ProductAdapter(blogProducts, fireStoreClass, false);
        binding.rclBlogDetails1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rclBlogDetails1.setAdapter(adapter);
        binding.rclBlogDetails1.setHasFixedSize(true);
    }

    private void addEvents() {
        setSupportActionBar(binding.toolbardetail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        binding.toolbardetail.setNavigationOnClickListener(v -> finish());
    }
    private void initBlog() {
        binding.rclBlogDetails2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ArrayList<Blogs> blogs = new ArrayList<>();
        blogs.add(new Blogs("5 tiêu chuẩn thức ăn cho mèo mà một Sen chính hiệu cần biết", R.drawable.blog2_image_4, "14.02.2024", "1. Giảm lượng tinh bột trong khẩu phần ăn mỗi ngày. Đúng rằng con người không thể sống thiếu ...",2));
        blogs.add(new Blogs("Những lưu ý khi triệt sản chó cái", R.drawable.blog2_image_5, "17.02.2024", "1. Triệt sản là gì?Triệt sản (hay thiến) đây là một phẫu thuật loại bỏ cơ quan sinh dục của động vật. Việc này nhằm...",2));
        blogs.add(new Blogs("Cách xử lý vết thương khi bị chó cắn", R.drawable.blog2_image_6, "20.3.2024", "Hiện nay bệnh dại chưa có thuốc điều trị đặc hiệu. Xử lý vết thương khi bị chó cắn đúng cách và được tiêm vắc-xin...",2));
        blogs.add(new Blogs("Top 7 giống chó dễ nuôi nhất", R.drawable.blog_image, "20.3.2024", "1. Chó ChihuahuaChihuahua là giống chó đang được nuôi khá nhiều tại Việt Nam. Lý do dòng chó này được yêu chuộng một cách rộng...",2));
        blogAdapter = new BlogAdapter(blogs);
        binding.rclBlogDetails2.setAdapter(blogAdapter);
    }

}