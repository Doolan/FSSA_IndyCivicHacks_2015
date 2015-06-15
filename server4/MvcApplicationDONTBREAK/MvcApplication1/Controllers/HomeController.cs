using System;
using System.Web.Mvc;
using System.Web.Script.Serialization;
namespace MvcApplication1.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            ViewBag.Message = "Modify this template to jump-start your ASP.NET MVC application.";

            return View();
        }

        public ActionResult About()
        {
            ViewBag.Message = "Your app description page.";

            return View(); 
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";

            return View();
        }
        
        public class CheckinData {
            public string firstName { get; set; }
            public string lastName { get; set; }
            public int zipcode { get; set; }
            public int ssn { get; set; }
            public DateTime dob { get; set; }
        }

        [HttpPost]
        public bool checkin(string stuff)
        {
            Console.WriteLine(stuff);
            JavaScriptSerializer serializer = new JavaScriptSerializer();
            CheckinData data = serializer.Deserialize<CheckinData>(stuff);
            return checkUserIn(data);
        }


        [HttpPost]
        public bool setReason(string reason)
        {
            return updateReason(reason);
        }
        
        public static bool updateReason(string reason){
            FSSAEntities db = new FSSAEntities();
            db.update_reason(reason, 5);
            return true;
        }


        public static bool checkUserIn(CheckinData data){

            FSSAEntities db = new FSSAEntities();
            int id = db.get_register_login(data.firstName, data.lastName, data.zipcode, data.ssn, data.dob, 5);
            System.Web.HttpContext.Current.Session["checkinID"] = id;
            return true;
        }

      

    }
}
