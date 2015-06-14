using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using MvcApplication1.Models;

namespace MvcApplication1.Controllers
{
    public class QueueController : Controller
    {
        string queueDetailPane;
        public ActionResult Index()
        {
            ViewBag.Message = "Queue Overview";
            ViewBag.Location = 5;
            return View();
        }


        [HttpPost]
        public ActionResult SetDetails(string queueName)
        {
            //ViewBag.Message = "QueueDetail";
           // ViewBag.Queue = queueName;
            System.Web.HttpContext.Current.Session["queueName"] = queueName;
            //queueDetailPane = queueName;
            //Console.WriteLine(queueName);
           // string a = queueName.ToString();
            //Packet b = (Packet)queueName;
            //Console.WriteLine(queueName);
            //ViewBag.Location = 5;
            //return View();
            return Redirect("Details");
        }

        public ActionResult Details()
        {
            ViewBag.Queue = System.Web.HttpContext.Current.Session["queueName"];
            ViewBag.Location = 5;
            return View();
        }

        public ActionResult indexer()
        {
            return null;
        }


        public ActionResult QueueClick(string name)
        {


            return null;
        }
 
        //[Authorize]
        public static List<get_Queue_Status_Result> GetQueueNames()
        {
            FSSAEntities db = new FSSAEntities();
            List<get_Queue_Status_Result> queues = db.get_Queue_Status(5).ToList<get_Queue_Status_Result>();

           // return RedirectToAction("GetQueueNames",queues);
            return queues;
        }

        public static List<get_Queue_Details_Result> GetQueueDetails(string queueName, int location)
        {
            FSSAEntities db = new FSSAEntities();
            List<get_Queue_Details_Result> details = db.get_Queue_Details(queueName, location).ToList<get_Queue_Details_Result>();
            return details;
        }

    }
}
