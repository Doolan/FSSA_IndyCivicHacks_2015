//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace MvcApplication1
{
    using System;
    using System.Collections.Generic;
    
    public partial class Queue
    {
        public Queue()
        {
            this.Vists = new HashSet<Vist>();
        }
    
        public short id { get; set; }
        public string Name { get; set; }
    
        public virtual ICollection<Vist> Vists { get; set; }
    }
}