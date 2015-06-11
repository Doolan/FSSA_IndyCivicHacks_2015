using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace MvcApplication1.Controllers
{
    public class QueueController : Controller
    {
        public ActionResult QueueDetail()
        {
            ViewBag.Message = "QueueDetail";
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


        // GET: /Queue/

        public ActionResult Index()
        {
            return View();
        }

        //[Authorize]
        public static List<get_Queue_Status_Result> GetQueueNames()
        {
            FSSAEntities db = new FSSAEntities();
            List<get_Queue_Status_Result> queues = db.get_Queue_Status(5).ToList<get_Queue_Status_Result>();

           // return RedirectToAction("GetQueueNames",queues);
            return queues;
        }

    }
}
