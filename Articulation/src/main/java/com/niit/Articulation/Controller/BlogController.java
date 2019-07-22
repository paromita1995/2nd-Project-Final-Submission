package com.niit.Articulation.Controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.Articulation.Dao.BlogCommentDao;
import com.niit.Articulation.Dao.BlogDao;
import com.niit.Articulation.Dao.BlogLikeDao;
import com.niit.Articulation.Dao.NotificationDao;
import com.niit.Articulation.Model.Blog;
import com.niit.Articulation.Model.BlogComment;
import com.niit.Articulation.Model.BlogLike;
import com.niit.Articulation.Model.JobApplication;
import com.niit.Articulation.Model.Notification;
import com.niit.Articulation.Model.UserDetails;

@RestController
public class BlogController {
	@Autowired
	BlogDao bd;
	@Autowired
	UserDetails user;
	@Autowired
	BlogCommentDao comment;
	@Autowired
	Blog blog;
	@Autowired
	BlogLikeDao Bld;
	@Autowired
	BlogLike bloglike;
	@Autowired
	NotificationDao notificationDao;
	@Autowired
	Notification notification;
	
	//**********************************BLOG ****************************************************************//
	//*******************************************************************************************************//
	
//1. getAll blogList...........!
	
	@RequestMapping(value="/bloglist", method=RequestMethod.GET)
	public ResponseEntity<List<Blog>> getAllBlogs(){
		List<Blog> blog=bd.getAllBlogs();
		if(blog.isEmpty()){
			return new ResponseEntity<List<Blog>>(blog, HttpStatus.NO_CONTENT);
		}
		System.out.println(blog.size());
		System.out.println("retrieving blogs ");
		return new ResponseEntity<List<Blog>>(blog, HttpStatus.OK);

}
	
//2. create blog.........!
	
	@RequestMapping(value="/createNewBlog/", method=RequestMethod.POST)
	public ResponseEntity<Blog> creatBlog(@RequestBody Blog blog,HttpSession session){
		System.out.println("Create Blog");
		
		UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
		
		
		
		blog.setBlogDate(new Date());
		blog.setBlogStatus("Pending");
		
		blog.setUserId(user.getUserId());
		 blog.setBlogCountLike(0);
			blog.setBlogCommentCount(0);
		bd.saveBlog(blog);
		System.out.println("blog" + blog.getBlogId());
		return new ResponseEntity<Blog>(blog,HttpStatus.OK);
		
		
		}
	


//3.  delete blog .............!
		
		@RequestMapping(value="/blog/{id}", method=RequestMethod.DELETE)
		public ResponseEntity<Blog>delete(@PathVariable("id")int id){
			Blog blog=bd.getBlogByBlogId(id);
			if(blog == null){
				blog = new Blog();
				blog.setErrorMessage("Blog does not exist with id : " + id);
				return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
				
			}
			bd.deleteBlog(blog);
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		
		
//4. Update Blog by Id..............!
		
		@RequestMapping(value="/updateBlog/{id}", method=RequestMethod.PUT)
		public ResponseEntity<Blog> updateblog(@PathVariable("id") int id, @RequestBody Blog blog,HttpSession session){
			UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
			if(bd.getAllBlogs()==null){
				blog =new Blog();
				blog.setErrorMessage("blog does not exist with id : " +blog.getBlogId());
				return new ResponseEntity<Blog>(blog, HttpStatus.NO_CONTENT);
			}
			blog.setBlogStatus("Pending");
		    blog.setBlogDate(new Date(System.currentTimeMillis()));
		    blog.setUserId(user.getUserId());
		    blog.setBlogCountLike(0);
			blog.setBlogCommentCount(0);
			bd.updateBlog(blog);
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		
		
		
		
		
//5. check blog by {id}............!
		
				@RequestMapping(value= "/blog/{id}",method=RequestMethod.GET)
				public ResponseEntity<Blog>getBlogByBlogId(@PathVariable("id")int id){
					Blog blog = bd. getBlogByBlogId(id);
					if (blog == null){
						blog = new Blog();
						blog.setErrorMessage("blog does not exist with id : " + id);
							return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
							
					}
					return new ResponseEntity<Blog>(blog, HttpStatus.OK);
				}
		
//6. Showlist of Approved blogs...............!
				
				@RequestMapping(value="/approvedListOfBlog", method=RequestMethod.GET)
				public ResponseEntity<List<Blog>> getAllApproveBlogs(){
					List<Blog> blog=bd.getAllApproveBlogs();
					if(blog.isEmpty()){
						return new ResponseEntity<List<Blog>>(blog, HttpStatus.NO_CONTENT);
					}
					System.out.println(blog.size());
					System.out.println(" displaying approved blogs ");
					return new ResponseEntity<List<Blog>>(blog, HttpStatus.OK);

			}
//7.Showlist of Pending blogs...........!
				
				@RequestMapping(value="/unapprovedBlogList", method=RequestMethod.GET)
				public ResponseEntity<List<Blog>> getAllPendingBlogs(){
					List<Blog> blog=bd.getAllPendingBlogs();
					if(blog.isEmpty()){
						return new ResponseEntity<List<Blog>>(blog, HttpStatus.NO_CONTENT);
					}
					System.out.println(blog.size());
					System.out.println(" displaying UnApproved Blogs ");
					return new ResponseEntity<List<Blog>>(blog, HttpStatus.OK);
				}
//Blog Approved by Admin...........!
				
				@RequestMapping(value="/approvedBlog/{id}", method=RequestMethod.PUT)
				public ResponseEntity<Blog>approveBlog(@PathVariable("id") int id,HttpSession session)
				{	
					
						Blog blogs=bd.getBlogByBlogId(id);
						if(((UserDetails)session.getAttribute("loggedInUser")).getRole().equals("ADMIN")&&
								(blogs!=null))
						{
									blogs.setBlogStatus("Approved");
									bd.updateBlog(blogs);
									Notification notification=new Notification();
								    notification.setApprovedOrRejected("Approved");
								    notification.setBlogTitle(blogs.getBlogTitle());
							    	notification.setUserId(blogs.getUserId());//AUTHOR OF THE BLOGPOST
							    	notification.setViewed("False");
									notificationDao.addNotification(notification);
									return new ResponseEntity<Blog>(blogs,HttpStatus.OK);
								}
						else
						{
									
									return new ResponseEntity<Blog>(blogs,HttpStatus.NOT_FOUND);
								}
				}

//Blog Rejected by Admin..........!
				@RequestMapping(value="/rejectedBlog/{id}", method=RequestMethod.PUT)
				public ResponseEntity<Blog>rejectBlog(@PathVariable("id") int id,HttpSession session)
				{	
					
						Blog blogs=bd.getBlogByBlogId(id);
						if(((UserDetails)session.getAttribute("loggedInUser")).getRole().equals("ADMIN")&&
								(blogs!=null))
						{
									blogs.setBlogStatus("Reject");
									bd.updateBlog(blogs);
									Notification notification=new Notification();
								    notification.setApprovedOrRejected("Rejected");
								    notification.setBlogTitle(blogs.getBlogTitle());
							    	notification.setUserId(blogs.getUserId());//AUTHOR OF THE BLOGPOST
							    	notification.setRejectionReason( "REJECTED BY ADMIN");
							    	notification.setViewed("False");//ENTERED BY ADMIN
									notificationDao.addNotification(notification);
									return new ResponseEntity<Blog>(blogs,HttpStatus.OK);
								}
						else
						{
									
									return new ResponseEntity<Blog>(blogs,HttpStatus.NOT_FOUND);
								}
						
					}
				
				//get my Blog..............!
				@RequestMapping(value= "/myBlog",method=RequestMethod.GET)
				public ResponseEntity<List<Blog>>getBlogs(HttpSession session){
				UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
				return new ResponseEntity<List<Blog>>(bd.getMyBlogs(user.getUserId()),HttpStatus.OK);
				}	
				
	
//************************************** BLOGLIKE ****************************************************************//				
//****************************************************************************************************************//				


				@RequestMapping(value="/likeBlog/{blogId}", method=RequestMethod.PUT)
				public ResponseEntity<Blog> likeBlog(@PathVariable("blogId") int blogId,HttpSession session) 
				{
					try
					{
						UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
						System.out.println(user.getName());
						Blog blog = bd.getBlogByBlogId(blogId);
						if (blog == null)
						{
							blog = new Blog();
							
							blog.setErrorMessage("No blog exist with id : " + blogId);
				
							return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
					}
					
						else if(Bld.isExist(blogId, user.getUserId()))
						{
							blog = new Blog();
							
							blog.setErrorMessage("User has already liked the blog: " + blogId);

							return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
						}
						
					blog.setBlogCountLike(blog.getBlogCountLike()+1);
					bd.updateBlog(blog);
					BlogLike blogLike=new BlogLike();
					blogLike.setBlogId(blogId);blogLike.setUserId(user.getUserId());blogLike.setUserName(user.getName());
					blogLike.setBlogLikeDate(new Date(System.currentTimeMillis()));
					Bld.saveBlogLike(blogLike);
					return new ResponseEntity<Blog>(blog, HttpStatus.OK);
					}
					catch(NullPointerException e)
					{
						e.printStackTrace();
						Blog blog=new Blog();
						blog.setErrorMessage("not logged in");
						blog.setErrorCode("404");
						return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);

					}
					
					
				}
					
					


				
				
				
				
//1. list of BlogLike by BlodId............!
				@RequestMapping(value="/bloglike/{blogId}", method=RequestMethod.GET)
				@ResponseBody
				public ResponseEntity<List<BlogLike>> getBlogLikesByblogId(@PathVariable("blogId") int blogId,HttpSession session) {
					Blog blog = bd.getBlogByBlogId(blogId);
					if (blog == null) {
						return new ResponseEntity<List<BlogLike>>(HttpStatus.NO_CONTENT);
					}
					return new ResponseEntity<List<BlogLike>>(Bld.getBlogLikesByBlogId(blogId), HttpStatus.OK);
					
				}
				
				
//3. save bloglike...................!
				
				@RequestMapping(value="/createNewBloglike/", method=RequestMethod.POST)
				public ResponseEntity<BlogLike> saveBlogLike(@RequestBody BlogLike bL,HttpSession session){
					System.out.println("Create Bloglike");
					
					UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
					
					
					
					bL.setBlogLikeDate(new Date());
					
					bL.setUserName(user.getName());
					bL.setUserId(user.getUserId());
					Bld.saveBlogLike(bL);
					System.out.println("bloglike" + blog.getBlogId());
					return new ResponseEntity<BlogLike>(bL,HttpStatus.OK);
					
					
					}
				
//************************************BLOG COMMENT *********************************************************//
//*********************************************************************************************************//
	
//1. getAll Commentlist..................!
				
				@RequestMapping(value="/blogCommentlist", method=RequestMethod.GET)
				public ResponseEntity<List<BlogComment>> getAllBlogComments(){
					List<BlogComment>blogComment=comment.getAllBlogComments();
					if(blogComment.isEmpty()){
						return new ResponseEntity<List<BlogComment>>(blogComment, HttpStatus.NO_CONTENT);
					}
					System.out.println(blogComment.size());
					System.out.println("retrieving blogComments ");
					return new ResponseEntity<List<BlogComment>>(blogComment, HttpStatus.OK);

			}
				
// 2. create NewComment..................!
				
				@RequestMapping(value="/createBlogComment/", method=RequestMethod.POST)
				public ResponseEntity<BlogComment> createBlogComment(@RequestBody BlogComment blogComment,HttpSession session){
					System.out.println("Create BlogComment");
					
					UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
					
					blogComment.setBlogCommentDate(new Date());
					blogComment.setUserId(user.getUserId());
					blogComment.setUserName(user.getName());
					blogComment.setBlogId(blogComment.getBlogId());
					comment.save(blogComment);
					blog.setBlogCommentCount(blog.getBlogCommentCount()+1);
					System.out.println("at blog comment count**");
					bd.updateBlog(blog);
					System.out.println("blogComment" + blogComment.getBlogCommentId());
					return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
			}
				
// 3. Update Blog Comment..................!
				
				@RequestMapping(value="/updateBlogComment/{id}", method=RequestMethod.PUT)
				public ResponseEntity<BlogComment> update(@PathVariable("id") int id, @RequestBody BlogComment blogcom,HttpSession session){
					UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
					if(comment.getAllBlogComments()==null){
						blogcom =new BlogComment();
						blogcom.setErrorMessage("blog does not exist with id : " +blog.getBlogId());
						return new ResponseEntity<BlogComment>(blogcom, HttpStatus.NO_CONTENT);
					}
					//blogcom.setUserName(user.getName());
				    blogcom.setBlogCommentDate(new Date(System.currentTimeMillis()));
				   // blogcom.setUserId(user.getUserId());
				    //blogcom.setBlogId(id);
				    comment.update(blogcom);
					return new ResponseEntity<BlogComment>(blogcom, HttpStatus.OK);
				}
				
//4. saveOrUpdate				
//5. Delete bolg Comments..................! 

				@RequestMapping(value="/BlogCommentDelete/{id}", method=RequestMethod.DELETE)
				public ResponseEntity<BlogComment>deleteBlogComment(@PathVariable("id")int id){
					BlogComment blogComment=comment.getByBlogCommentId(id);
					if(blogComment== null){
						blogComment = new BlogComment();
						blogComment.setErrorMessage("BlogComment does not exist with id : " + id);
						return new ResponseEntity<BlogComment>(blogComment, HttpStatus.NOT_FOUND);
						
					}
					comment.delete(blogComment);
					return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
				}				
//6. Check BlogComment by {id}.............!
				
				@RequestMapping(value= "/commentsById/{id}",method=RequestMethod.GET)
				public ResponseEntity<BlogComment>getByBlogCommentId(@PathVariable("id")int id){
					BlogComment blogComment = comment. getByBlogCommentId(id);
					if (blogComment == null){
						blogComment = new BlogComment();
						blogComment.setErrorMessage("BlogComment does not exist with id : " + id);
							return new ResponseEntity<BlogComment>(blogComment, HttpStatus.NOT_FOUND);
							
					}
					return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
				}
				
				
//7. Show blogComment by BlogId..............!
				 
				@RequestMapping(value= "/commentByBlogId/{id}",method=RequestMethod.GET)
				public ResponseEntity<List<BlogComment>>listByBlogId(@PathVariable("id") int id){
					List<BlogComment>blogComment =comment.listByBlogId(id);
					if (blogComment == null){
						blog.setErrorMessage("blogComment does not exist with userId : " + id);
							return new ResponseEntity<List<BlogComment>>(blogComment, HttpStatus.NOT_FOUND);
							
					}
					return new ResponseEntity<List<BlogComment>>(blogComment, HttpStatus.OK);
				}			
				
				
				
				
//8. Show BlogComment By userId..............!
				@RequestMapping(value= "/commentByUserId/{id}",method=RequestMethod.GET)
				public ResponseEntity<List<BlogComment>>getlistByUserId(@PathVariable("id") String id){
					List<BlogComment>blogComment =comment.listByUserId(id);
					if (blogComment == null){
						blog.setErrorMessage("Comment does not exist with userId : " + id);
							return new ResponseEntity<List<BlogComment>>(blogComment, HttpStatus.NOT_FOUND);
							
					}
					return new ResponseEntity<List<BlogComment>>(blogComment, HttpStatus.OK);
				}
				
				
	
				
				
		
				
}
