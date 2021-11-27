var pages = document.getElementsByClassName('page');
  for(var i = 0; i < pages.length; i++)
    {
      var page = pages[i];
      if (i % 2 === 0)
        {
          page.style.zIndex = (pages.length - i);
        }
    }

  document.addEventListener('DOMContentLoaded', function(){
    for(var i = 0; i < pages.length; i++)
      {
        //Or var page = pages[i];
        pages[i].pageNum = i + 1;
        pages[i].onclick=function()
          {
            console.log("test");
            if (this.pageNum % 2 === 0)
              {
                this.classList.remove('flipped');
                this.previousElementSibling.classList.remove('flipped');
                console.log("Print:" + this.pageNum);
              }
            else
              {
                this.classList.add('flipped');
                this.nextElementSibling.classList.add('flipped');
                console.log("Print:" + this.pageNum);
              }
           }
        }
        setTimeout(function(){
          pages[0].classList.add('flipped');
          pages[0].nextElementSibling.classList.add('flipped');
         }, 200);
      
  })


  // });
 