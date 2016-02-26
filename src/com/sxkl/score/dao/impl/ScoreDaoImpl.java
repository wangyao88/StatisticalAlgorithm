package com.sxkl.score.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.sxkl.common.utils.PageNoUtil;
import com.sxkl.score.dao.ScoreDao;
import com.sxkl.score.model.Score;
import com.sxkl.target.model.Target;

@Repository("scoreDaoImpl")
public class ScoreDaoImpl extends HibernateDaoSupport implements ScoreDao{

	@Autowired  
    public void setSessionFactoryOverride(SessionFactory sessionFactory){  
        super.setSessionFactory(sessionFactory);  
    }

	@SuppressWarnings("unchecked")
	public List<Score> getScoreByPersonId(String personId) {
		final Object[] param = new Object[]{personId};
		final String hql = "from Score s where s.person.id=?";
		List<Score> scores = (List<Score>) getHibernateTemplate().execute(
				new HibernateCallback() {
			           public Object doInHibernate(Session session)throws HibernateException{
				            List list = PageNoUtil.getList(session,hql,param);
				            return list;
			           }
				});
		return scores;
	}

	public void addScore(Score scoreBean) {
		this.getHibernateTemplate().save(scoreBean);
	}

	@SuppressWarnings("unchecked")
	public Score getScoreByParam(final String markPlanId, final String personId,final String targetId) {
//		String hql = "from Score s where s.markPlan.id=? and s.target.id=? and s.person.id=?";
//		String[] param = new String[]{markPlanId,targetId,personId};
//		List<Score> scores =  (List<Score>) this.getHibernateTemplate().find(hql, param);
		final String hql = "from Score s where s.markPlan.id= :markPlanId and s.target.id= :targetId and s.person.id= :personId";
		List<Score> scores = (List<Score>) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				query.setParameter("markPlanId", markPlanId);
				query.setParameter("targetId", targetId);
				query.setParameter("personId", personId);
				List<Target> list = query.list();
				if(list == null || list.size() == 0){
					return new ArrayList<Target>();
				}
				return list;
			}
		});
		if(scores != null && scores.size() > 0){
			return scores.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Score> checkMarkByMarkPlanId(final String markPlanId) {
//		String hql = "from Score s where s.markPlan.id=?";
//		String[] param = new String[]{markPlanId};
//		List<Score> scores =  (List<Score>) this.getHibernateTemplate().find(hql, param);
		final String hql = "from Score s where s.markPlan.id= :markPlanId";
		List<Score> scores = (List<Score>) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				query.setParameter("markPlanId", markPlanId);
				List<Target> list = query.list();
				if(list == null || list.size() == 0){
					return new ArrayList<Target>();
				}
				return list;
			}
		});
		return scores;
	}

	@SuppressWarnings("unchecked")
	public List<Score> getScoreByPersonAndMarkPlan(final String personId,final String markPlanId) {
//		String hql = "from Score s where s.person.id=? and s.markPlan.id=? order by s.target.level asc";
//		String[] param = new String[]{personId,markPlanId};
//		List<Score> scores =  (List<Score>) this.getHibernateTemplate().find(hql, param);
		final String hql = "from Score s where s.person.id= :personId and s.markPlan.id= :markPlanId order by s.target.level asc";
		List<Score> scores = (List<Score>) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				query.setParameter("personId", personId);
				query.setParameter("markPlanId", markPlanId);
				List<Target> list = query.list();
				if(list == null || list.size() == 0){
					return new ArrayList<Target>();
				}
				return list;
			}
		});
		if(scores == null){
			return new ArrayList<Score>();
		}
		return scores;
	}

	public void updateScore(Score temp) {
		this.getHibernateTemplate().update(temp);
	}

	@SuppressWarnings("unchecked")
	public List<Score> getScoreByMarkPlanId(final String markPlanId) {
//		String hql = "from Score s where s.markPlan.id=? order by s.target.level asc";
//		String[] param = new String[]{markPlanId};
//		List<Score> scores =  (List<Score>) this.getHibernateTemplate().find(hql, param);
		final String hql = "from Score s where s.markPlan.id= :markPlanId order by s.target.level asc";
		List<Score> scores = (List<Score>) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				query.setParameter("markPlanId", markPlanId);
				List<Target> list = query.list();
				if(list == null || list.size() == 0){
					return new ArrayList<Target>();
				}
				return list;
			}
		});
		if(scores == null){
			return new ArrayList<Score>();
		}
		return scores;
	}

}
