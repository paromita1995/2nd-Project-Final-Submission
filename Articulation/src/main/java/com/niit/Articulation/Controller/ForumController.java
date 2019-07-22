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
import org.springframework.web.bind.annotation.RestController;

import com.niit.Articulation.Dao.ForumCommentDao;
import com.niit.Articulation.Dao.ForumDao;
import com.niit.Articulation.Model.Blog;
import com.niit.Articulation.Model.BlogComment;
import com.niit.Articulation.Model.Forum;
import com.niit.Articulation.Model.ForumComment;
import com.niit.Articulation.Model.UserDetails;



@RestController
public class ForumController {
	@Autowired
	ForumDao forumD;
	@Autowired
	UserDetails user;
	@Autowired
	ForumCommentDao comment;
	@Autowired
	Forum forum;
	
//**************************************** FORUM ***************************************************************//	
//**************************************************************************************************************//
	
//1.Create forum..............!
	
			@RequestMapping(value="/createNewForum/", method=RequestMethod.POST)
			public ResponseEntity<Forum> createForum(@RequestBody Forum forum,HttpSession session){
				System.out.println("Create Forum");
				
				UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
				forum.setForumCreationDate(new Date());
				forum.setForumStatus("Pending");
				forum.setUserName(user.getName());
				forum.setUserId(user.getUserId());
				
				forumD.saveForum(forum);
				System.out.println("forum" + forum.getForumId());
				return new ResponseEntity<Forum>(forum,HttpStatus.OK);
	}
			
//2.Delete forum .............!	
			
			@RequestMapping(value="/deleteForum/{id}", method=RequestMethod.DELETE)
			public ResponseEntity<Forum>deleteForum(@PathVariable("id")int id){
				Forum fr=forumD.getForumByForumId(id);
				if(fr == null){
					fr = new Forum();
					fr.setErrorMessage("Forum does not exist with id : " + id);
					return new ResponseEntity<Forum>(fr, HttpStatus.NOT_FOUND);
					
				}
				forumD.deleteForum(fr);
				return new ResponseEntity<Forum>(fr, HttpStatus.OK);
			}
			
//3.Update Forum by Id...........!
			
			@RequestMapping(value="/updateForum/{id}", method=RequestMethod.PUT)
			public ResponseEntity<Forum> updateforum(@PathVariable("id") int id, @RequestBody Forum forum,HttpSession session){
				UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
				if(forumD.getAllForums()==null){
					forum =new Forum();
					forum.setErrorMessage("Forum does not exist with id : " +forum.getForumId());
					return new ResponseEntity<Forum>(forum, HttpStatus.NO_CONTENT);
				}
				forumD.updateForum(forum);
				return new ResponseEntity<Forum>(forum, HttpStatus.OK);
			}
			
//4.Check forum by {id}.............!
			
			@RequestMapping(value= "/forumById/{id}",method=RequestMethod.GET)
			public ResponseEntity<Forum>getForumByForumId(@PathVariable("id")int id){
				Forum forum = forumD. getForumByForumId(id);
				if (forum == null){
					forum = new Forum();
					forum.setErrorMessage("Forum does not exist with id : " + id);
						return new ResponseEntity<Forum>(forum, HttpStatus.NOT_FOUND);
						
				}
				return new ResponseEntity<Forum>(forum, HttpStatus.OK);
			}
	
	
//5.Show All forumlist...............!
			
	@RequestMapping(value="/forumlist", method=RequestMethod.GET)
	public ResponseEntity< List<Forum>> getAllForums(){
		 List<Forum> forum=forumD.getAllForums();
		if(forum.isEmpty()){
			return new ResponseEntity< List<Forum>>(forum, HttpStatus.NO_CONTENT);
		}
		System.out.println(forum.size());
		System.out.println("retrieving forums ");
		return new ResponseEntity< List<Forum>>(forum,HttpStatus.OK);

}
//6.Show all Approved forumlist............!
				@RequestMapping(value="/Approvedforumlist", method=RequestMethod.GET)
				public ResponseEntity< List<Forum>> getAllApprovedForums(){
					 List<Forum> forum=forumD.getAllApprovedForums();
					if(forum.isEmpty()){
						return new ResponseEntity< List<Forum>>(forum, HttpStatus.NO_CONTENT);
					}
					System.out.println(forum.size());
					System.out.println("Approved Forums ");
					return new ResponseEntity< List<Forum>>(forum,HttpStatus.OK);

			}
				
//7.Show all UnApproved forumlist....................!
				
				@RequestMapping(value="/UnApprovedforumlist", method=RequestMethod.GET)
				public ResponseEntity< List<Forum>> getAllRejectedForums(){
					 List<Forum> forum=forumD.getAllRejectedForums();
					if(forum.isEmpty()){
						return new ResponseEntity< List<Forum>>(forum, HttpStatus.NO_CONTENT);
					}
					System.out.println(forum.size());
					System.out.println("UnApproved Forums ");
					return new ResponseEntity< List<Forum>>(forum,HttpStatus.OK);

			}
				
 //Forum Approved by Admin............!
				@RequestMapping(value="/approvedForums/{id}", method=RequestMethod.PUT)
				public ResponseEntity<Forum>approveForum(@PathVariable("id") int id,HttpSession session)
				{	
					
						Forum forums=forumD.getForumByForumId(id);
						if(((UserDetails)session.getAttribute("loggedInUser")).getRole().equals("ADMIN")&&
								(forums!=null))
						{
									forums.setForumStatus("Approved");
									forumD.updateForum(forums);
									return new ResponseEntity<Forum>(forums,HttpStatus.OK);
								}
						else
						{
									
									return new ResponseEntity<Forum>(forums,HttpStatus.NOT_FOUND);
								}
						
					}
				
//Forum Rejected by Admin..............!
				
				@RequestMapping(value="/rejectForums/{id}", method=RequestMethod.PUT)
				public ResponseEntity<Forum>rejectForum(@PathVariable("id") int id,HttpSession session)
				{	
					
						Forum forums=forumD.getForumByForumId(id);
						if(((UserDetails)session.getAttribute("loggedInUser")).getRole().equals("ADMIN")&&
								(forums!=null))
						{
									forums.setForumStatus("Reject");
									forumD.updateForum(forums);
									return new ResponseEntity<Forum>(forums,HttpStatus.OK);
								}
						else
						{
									
									return new ResponseEntity<Forum>(forums,HttpStatus.NOT_FOUND);
								}
						
					}

				//get my Forum..............!
				@RequestMapping(value= "/ownForum",method=RequestMethod.GET)
				public ResponseEntity<List<Forum>>getForums(HttpSession session){
				UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
				return new ResponseEntity<List<Forum>>(forumD.getMyForums(user.getUserId()),HttpStatus.OK);
				}	
			
				
				
				
				
//****************************************** FORUMCOMMENT CONTROLLER **************************************************//
//**************************************************************************************************************//				

//1.Create NewComment............!
				
				@RequestMapping(value="/createNewComment/", method=RequestMethod.POST)
				public ResponseEntity<ForumComment> createForumComment(@RequestBody ForumComment forumComment,HttpSession session){
					System.out.println("Create ForumComment");
					
					UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
					
					forumComment.setForumCommentDate(new Date());
					forumComment.setUserId(user.getUserId());
					forumComment.setUserName(user.getName());
					comment.save(forumComment);
					System.out.println("forumComment" + forumComment.getForumCommentId());
					return new ResponseEntity<ForumComment>(forumComment,HttpStatus.OK);
			}
//2.Update forumcomment by {id}...............!
				
				
				@RequestMapping(value="/updateForumComment/{id}", method=RequestMethod.PUT)
				public ResponseEntity<ForumComment> update(@PathVariable("id") int id, @RequestBody ForumComment forumcom,HttpSession session){
					UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
					if(comment.getAllForumComments()==null){
						forumcom =new ForumComment();
						forumcom.setErrorMessage("blog does not exist with id : " +forum.getForumId());
						return new ResponseEntity<ForumComment>(forumcom, HttpStatus.NO_CONTENT);
					}
					//blogcom.setUserName(user.getName());
				    forumcom.setForumCommentDate(new Date(System.currentTimeMillis()));
				   // blogcom.setUserId(user.getUserId());
				    //blogcom.setBlogId(id);
				    comment.update(forumcom);
					return new ResponseEntity<ForumComment>(forumcom, HttpStatus.OK);
				}
//3.saveOrUpdate.................!				
//4.Delete forumcomment by {id}................!
				
				@RequestMapping(value="/forumCommentDelete/{id}", method=RequestMethod.DELETE)
				public ResponseEntity<ForumComment>deleteForumComment(@PathVariable("id")int id){
					ForumComment fc=comment.getByForumCommentId(id);
					if(fc== null){
						fc = new ForumComment();
						fc.setErrorMessage("fc does not exist with id : " + id);
						return new ResponseEntity<ForumComment>(fc, HttpStatus.NOT_FOUND);
						
					}
					comment.delete(fc);
					return new ResponseEntity<ForumComment>(fc, HttpStatus.OK);
				}		
				
//5.Check forumComment by {id}..................!
				@RequestMapping(value= "/forumcommentsById/{id}",method=RequestMethod.GET)
				public ResponseEntity<ForumComment>getByForumCommentId(@PathVariable("id")int id){
					ForumComment forumComment = comment.getByForumCommentId(id);
					if (forumComment == null){
						forumComment = new ForumComment();
						forumComment.setErrorMessage("ForumComment does not exist with id : " + id);
							return new ResponseEntity<ForumComment>(forumComment, HttpStatus.NOT_FOUND);
							
					}
					return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
				}
				
//6.Show ForumComment By userId................!
				
				@RequestMapping(value= "/forumcommentByUserId/{id}",method=RequestMethod.GET)
				public ResponseEntity<List<ForumComment>>getlistByUserId(@PathVariable("id") String id){
					List<ForumComment>forumComment =comment.listByUserId(id);
					if (forumComment == null){
						forum.setErrorMessage("Comment does not exist with userId : " + id);
							return new ResponseEntity<List<ForumComment>>(forumComment, HttpStatus.NOT_FOUND);
							
					}
					return new ResponseEntity<List<ForumComment>>(forumComment, HttpStatus.OK);
				}
				
//7.Show ForumComment by ForumId..................!			
				
				@RequestMapping(value= "/commentByforumId/{id}",method=RequestMethod.GET)
				public ResponseEntity<List<ForumComment>>listByForumId(@PathVariable("id") int id){
					List<ForumComment>forumComment =comment.listByForumId(id);
					if (forumComment == null){
						forum.setErrorMessage("forumComment does not exist with userId : " + id);
							return new ResponseEntity<List<ForumComment>>(forumComment, HttpStatus.NOT_FOUND);
							
					}
					return new ResponseEntity<List<ForumComment>>(forumComment, HttpStatus.OK);
				}
				
				
//8.Show All Commentlist..............!
				@RequestMapping(value="/forumCommentlist", method=RequestMethod.GET)
				public ResponseEntity<List<ForumComment>> getAllForumComments(){
					List<ForumComment>fc=comment.getAllForumComments();
					if(fc.isEmpty()){
						return new ResponseEntity<List<ForumComment>>(fc, HttpStatus.NO_CONTENT);
					}
					System.out.println(fc.size());
					System.out.println("retrieving forumComments ");
					return new ResponseEntity<List<ForumComment>>(fc, HttpStatus.OK);

			}
				
				
					
					
		
					
					
					
					
}

