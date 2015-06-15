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
        #region Views
        public ActionResult Index()
        {
            ViewBag.Message = "Queue Overview";
            ViewBag.Location = 5;
            return View();
        }

        public ActionResult Details()
        {
            ViewBag.Queue = System.Web.HttpContext.Current.Session["queueName"];
            ViewBag.Location = 5;
            return View();
        }

        public ActionResult Customer()
        {
           // ViewBag.CustomerID = System.Web.HttpContext.Current.Session["personId"];
           // ViewBag.QueueName = System.Web.HttpContext.Current.Session["queueName"];
           // ViewBag.Location = 5;
            Customer c = new Models.Customer();
            GetPersonDetails(c, (string)System.Web.HttpContext.Current.Session["queueName"], 5, (int)System.Web.HttpContext.Current.Session["personId"]);
            return View(c);
        }


        #endregion
        
        #region Ajax calls
        [HttpPost]
        public ActionResult SetDetails(string queueName)
        {
            System.Web.HttpContext.Current.Session["queueName"] = queueName;
            return Redirect("Details");
        }

        [HttpPost]
        public ActionResult ViewPerson(string personId)
        {
            int id;
            if(!Int32.TryParse(personId, out id)){
                return null;
            }            
            System.Web.HttpContext.Current.Session["personId"] = id;
            return Redirect("Customer");
         }

        [HttpPost]
        public bool RemovePerson(string personId)
        {
            int id;
            if (!Int32.TryParse(personId, out id))
            {
                return false;
            }
           return MarkedFinishedPerson(id);
           // System.Web.HttpContext.Current.Session["personId"] = id;
            //return Redirect("Customer");

        }



#endregion

        #region 
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

        public static void GetPersonDetails(Customer customer, string queueName, int location, int personID)
        {
            FSSAEntities db = new FSSAEntities();
            List<get_PersonInQueue_Details_Result> details = db.get_PersonInQueue_Details(queueName, location, personID).ToList<get_PersonInQueue_Details_Result>();
            customer.customerID = personID;
            customer.name = details[0].name;
            customer.dob = details[0].DOB;
            customer.dobdateString = details[0].DOB.ToShortDateString();
            customer.address = details[0].address;
            customer.zipcode = (int) details[0].zipcode;
            customer.mednum = (int) details[0].mednum;
            customer.reason = details[0].Reason;
            
            //return details[0];
        }


        public static bool MarkedFinishedPerson(int ID)//, string queueName)
        {
            string queueName = (string) System.Web.HttpContext.Current.Session["queueName"];
            FSSAEntities db = new FSSAEntities();
            db.update_Customer(queueName, 4, ID, 5);

            return true;
            
        }

        #endregion





    }
}
